// Copyright (c) 2019 Marco Marini, marco.marini@mmarini.org
//
// Licensed under the MIT License (MIT);
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// https://opensource.org/licenses/MIT
//
// Permission is hereby granted, free of charge, to any person
// obtaining a copy of this software and associated documentation
// files (the "Software"), to deal in the Software without
// restriction, including without limitation the rights to use,
// copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following
// conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
// OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.

package org.mmarini.fluid.model.v2;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

/**
 * Stores the temporary data for simulation
 *
 * @author mmarini
 *
 */
public class LocalContext1 implements Constants {
	private static final int[] CENTER = new int[] { 1, 1 };

	private static final INDArray NORMALS = Nd4j.create(new double[][][] {
			{ { -sqrt(0.5), -sqrt(0.5) }, { 0, -1 }, { sqrt(0.5), -sqrt(0.5) } }, { { -1, 0 }, { 0, 0 }, { 1, 0 } },
			{ { -sqrt(0.5), sqrt(0.5) }, { 0, 1 }, { sqrt(0.5), sqrt(0.5) } }, });

	private static final INDArray SECTIONS = Nd4j.create(new double[][] { { 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 } });

	private final SimulationContext1 context;
	private final INDArray momentum;
	private final INDArray constraints;
	private final INDArray energy;
	private final INDArray mass;
	private final INDArray pressure;
	private final INDArray speed;
	private INDArray freeMomentumFlux;
	private List<int[]> reactionSurfaces;
	private INDArray pressureForce;

	/**
	 *
	 * @param context
	 * @param indices
	 */
	public LocalContext1(final SimulationContext1 context, final int... indices) {
		this.context = context;
		momentum = Utils.local(context.getPadMomentum(), indices);
		constraints = Utils.local(context.getPadConstraints(), indices);
		energy = Utils.local(context.getPadEnergy(), indices);
		mass = Utils.local(context.getPadMass(), indices);
		pressure = Utils.local(context.getPadPressure(), indices);
		speed = Utils.local(context.getPadSpeed(), indices);
	}

	/**
	 *
	 * @param indices
	 * @return
	 */
	public double computeDeltaMass() {
		final double mu = constraints.getDouble(CENTER);
		final INDArray a = Utils.cellAddStamp(momentum);
		final INDArray s = NORMALS.mul(context.getCellArea()).muli(Utils.suffixBroadcast(SECTIONS, 2));
		final INDArray b = a.mul(s).sum(2);
		final double result = -b.sumNumber().doubleValue();
		return result * (1 - mu);
	}

	/**
	 *
	 * @return
	 */
	public INDArray computeDeltaMomentum() {
		final INDArray r = computeReaction();
		final INDArray dqf = getFreeMomentumFlux();
		final INDArray dq = dqf.add(r).mul(context.getInterval());
		final INDArray result = dq.sum(0, 1).reshape(1, 2);
		return result;
	}

	/**
	 *
	 * @return
	 */
	INDArray computeEnergyFlux() {
		final INDArray ef = Utils.mmul(speed, energy);
		final INDArray fx = Utils.cellAddStamp(ef);
		final INDArray fxs = fx.mul(-context.getCellArea());
		final INDArray incomeEnergyFluxes = Utils.mmul(fxs, NORMALS);
		final INDArray notMu = constraints.sub(1.0).negi();
		final INDArray withConstraints = incomeEnergyFluxes.mul(notMu);
		return withConstraints;
	}

	/**
	 * Returns the momentum flux without reaction force
	 * <p>
	 * <code>
	 * flux + pressure force
	 * </code>
	 * </p>
	 *
	 * @param indices
	 * @return the momentum flux vector
	 */
	private INDArray computeFreeMomentumFlux() {
		final INDArray mFlux = computeMomentumFlux();
		final INDArray pForce = getPressureForce();
		final INDArray result1 = mFlux.add(pForce);
		final INDArray result2 = result1.reshape(9, 2).sum(0);
		return result2;
	}

	/**
	 *
	 * @return
	 */
	INDArray computeM() {
		final int n = getReactionSurfaces().size();
		final INDArray m = Nd4j.zeros(n, n);
		for (int i = 0; i < n; i++) {
			final int[] ii = getReactionSurfaces().get(i);
			final INDArray ni = NORMALS.get(NDArrayIndex.point(ii[0]), NDArrayIndex.point(ii[1]));
			for (int j = 0; j < n; j++) {
				final int[] jj = getReactionSurfaces().get(j);
				final INDArray nj = NORMALS.get(NDArrayIndex.point(jj[0]), NDArrayIndex.point(jj[1]));
				final double r = ni.mmul(nj).getDouble(0);
				m.putScalar(new int[] { i, j }, r);
			}
		}
		return m;
	}

	/**
	 * Returns the momentum flux
	 * <p>
	 * <code>
	 * qijk (uijk . nk) + qij (uij . nk)
	 * </code>
	 * </p>
	 *
	 * @param indices
	 * @return
	 */
	INDArray computeMomentumFlux() {
		final INDArray q1 = momentum;
		final INDArray u1 = speed;
		final INDArray u1k = Utils.mmul(u1, NORMALS);
		// q1 (u1 . n)
		final INDArray q1k = Utils.mmul(q1, u1k);
		final INDArray u22 = Utils
				.prefixBroadcast(u1.get(NDArrayIndex.point(1), NDArrayIndex.point(1), NDArrayIndex.all()), 3, 3);

		// (u22 . n)
		final INDArray u22n = Utils.mmul(u22, NORMALS);
		final INDArray q22 = Utils
				.prefixBroadcast(q1.get(NDArrayIndex.point(1), NDArrayIndex.point(1), NDArrayIndex.all()), 3, 3);

		// q22 (u22 n)
		final INDArray q22k = Utils.mmul(q22, u22n);
		final INDArray result1 = q1k.add(q22k).neg();
		final INDArray result = Utils.mmul(result1, SECTIONS).mul(context.getCellArea());

		return result;
	}

	/**
	 *
	 * @return
	 */

	INDArray computePressurePower() {
		final INDArray uu = Utils.cellAddStamp(speed);
		final INDArray power = Utils.mmul(getPressureForce(), uu);
		return power;
	}

	/**
	 *
	 * @return
	 */
	INDArray computeReaction() {
		final INDArray v = computeV();
		final INDArray m = computeM();
		final INDArray u = v.mmul(m);

		final INDArray r = Nd4j.zeros(3, 3, 2);
		for (int k = 0; k < getReactionSurfaces().size(); ++k) {
			final int[] ind = getReactionSurfaces().get(k);
			final int i = ind[0];
			final int j = ind[1];
			final INDArray n = NORMALS.get(NDArrayIndex.point(i), NDArrayIndex.point(j), NDArrayIndex.all());
			final INDArray uv = n.mul(u.getDouble(k, 0));
			r.get(NDArrayIndex.point(i), NDArrayIndex.point(j), NDArrayIndex.all()).assign(uv);
		}
		return r;
	}

	/**
	 * Returns the list of indices of reaction surfaces for a given cell
	 * <p>
	 * The reaction surface is a reactive surface with positive out bound momentum
	 * flux.
	 *
	 * @param indices indices of cell
	 * @return the list of indices of reactive surfaces
	 */
	private List<int[]> computeReactionSurfaces() {
		final List<int[]> surfaces = computeReactiveSurfaces();
		final INDArray q22 = momentum.get(NDArrayIndex.point(1), NDArrayIndex.point(1));
		final INDArray q = q22.add(getFreeMomentumFlux().mul(context.getInterval()));
		final List<int[]> result = surfaces.stream().filter(idxs -> {
			final INDArray n = NORMALS.get(NDArrayIndex.point(idxs[0]), NDArrayIndex.point(idxs[1]));
			final double r = q.mul(n).sumNumber().doubleValue();
			return r > 0;
		}).collect(Collectors.toList());
		return result;
	}

	/**
	 * Returns the list of indices of reactive surfaces for a given cell
	 *
	 * @param indices indices of cell
	 * @return the list of indices of reactive surfaces
	 */
	List<int[]> computeReactiveSurfaces() {
		final INDArray map = constraints;
		final List<int[]> mapIndices = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				final int[] inds = new int[] { i, j };
				if (SECTIONS.getDouble(inds) > 0 && map.getInt(inds) != 0) {
					mapIndices.add(inds);
				}
			}
		}
		return mapIndices;
	}

	/**
	 * <p>
	 *
	 * <pre>
	 * V = (q22 + Delta q') n_k / Delta t
	 * </pre>
	 * </p>
	 *
	 * @param indices
	 * @param k
	 * @param dt
	 * @return
	 */
	INDArray computeV() {
		final INDArray q22 = momentum.get(NDArrayIndex.point(1), NDArrayIndex.point(1)).div(context.getInterval());
		final INDArray q = q22.add(getFreeMomentumFlux());
		final List<Double> m = getReactionSurfaces().stream().map(inds -> {
			final INDArray n = NORMALS.get(NDArrayIndex.point(inds[0]), NDArrayIndex.point(inds[1]));
			final double result = n.mul(q).sumNumber().doubleValue();
			return result;
		}).collect(Collectors.toList());
		final INDArray v = Nd4j.create(m).reshape(1, m.size());
		return v;
	}

	/**
	 * @return the constraints
	 */
	INDArray getConstraints() {
		return constraints;
	}

	/**
	 * @return the energy
	 */
	INDArray getEnergy() {
		return energy;
	}

	/**
	 * @return the freeMomentumFlux
	 */
	INDArray getFreeMomentumFlux() {
		if (freeMomentumFlux == null) {
			freeMomentumFlux = computeFreeMomentumFlux();
		}
		return freeMomentumFlux;
	}

	/**
	 * @return the mass
	 */
	INDArray getMass() {
		return mass;
	}

	/**
	 * @return the momentum
	 */
	INDArray getMomentum() {
		return momentum;
	}

	/**
	 * Returns the pressure force <code>
	 * -(p(ab) - p(22)) n(abc) s(ab)
	 * </code>
	 *
	 * @param indices
	 * @return
	 */
	INDArray getPressureForce() {
		if (pressureForce == null) {
			final INDArray mu = constraints;
			final INDArray mask = mu.sub(1.0).neg();
			final INDArray p = pressure;
			final INDArray dp = Utils.cellDifferences(p);
			final INDArray fs = Utils.mmul(NORMALS, dp);
			final INDArray f = Utils.mmul(fs, SECTIONS).mul(-context.getCellArea());
			final INDArray fc = Utils.mmul(f, mask);
			pressureForce = fc;
		}
		return pressureForce;
	}

	/**
	 *
	 * @return
	 */
	List<int[]> getReactionSurfaces() {
		if (reactionSurfaces == null) {
			reactionSurfaces = computeReactionSurfaces();
		}
		return reactionSurfaces;
	}
}