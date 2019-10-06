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

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.transforms.Pad.Mode;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

/**
 * Stores the temporary data for simulation
 *
 * @author mmarini
 *
 */
public class SimulationContext implements Constants {
	private static final int[][] VECTOR_PAD_WIDTHS = new int[][] { { 1, 1 }, { 1, 1 }, { 0, 0 } };

	private static final INDArray NORMALS = Nd4j.create(new double[][][] {
			{ { -sqrt(0.5), -sqrt(0.5) }, { -1, 0 }, { -sqrt(0.5), sqrt(0.5) } }, { { 0, -1 }, { 0, 0 }, { 0, 1 } },
			{ { sqrt(0.5), -sqrt(0.5) }, { 1, 0 }, { sqrt(0.5), sqrt(0.5) } }, });

	private static final INDArray SECTIONS = Nd4j.create(new double[][] { { 0, 1, 0 }, { 1, 0, 1 }, { 0, 1, 0 } });

	private final Universe universe;
	private final INDArray mass;
	private final INDArray momentum;
	private final INDArray padMass;
	private final INDArray padMomentum;
	private final INDArray padEnergy;
	private final INDArray padSpeed;
	private final INDArray padPressure;
	private final INDArray padConstraints;

	/**
	 *
	 * @param universe
	 */
	public SimulationContext(final Universe universe) {
		super();
		this.universe = universe;
		this.mass = universe.getDensity().mul(universe.getCellVolume());
		this.momentum = Utils.mmul(universe.getSpeed(), mass);

		final INDArray energy = universe.getTemperature().mul(mass).mul(universe.getSpecificHeatCapacity());
		padEnergy = Nd4j.pad(energy, 1, 1);
		padMass = Nd4j.pad(mass, 1, 1);
		padMomentum = Nd4j.pad(momentum, VECTOR_PAD_WIDTHS);
		padSpeed = Nd4j.pad(universe.getSpeed(), VECTOR_PAD_WIDTHS);
		final INDArray pressure = universe.getTemperature().mul(mass)
				.mul(R / universe.getCellVolume() / universe.getMolecularMass());
		padPressure = Nd4j.pad(pressure, 1, 1);
		padConstraints = Nd4j.pad(universe.getMassContraints(), new int[] { 1, 1 }, Mode.CONSTANT, 1.0);
	}

	/**
	 *
	 * @param indices
	 * @return
	 */
	double computeDeltaMass(final int... indices) {
		final double mu = universe.getMassContraints().getDouble(indices);
		final INDArray q = getLocalMomentum(indices);
		final INDArray a = Utils.cellAddStamp(q);
		final INDArray s = NORMALS.mul(universe.getCellArea()).muli(Utils.suffixBroadcast(SECTIONS, 2));
		final INDArray b = a.mul(s).sum(2);
		final double result = b.sumNumber().doubleValue();
		return result * (1 - mu);
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
	INDArray computeFreeMomentumFlux(final int... indices) {
		final INDArray mFlux = computeMomentumFlux(indices);
		final INDArray pForce = computePressureForce(indices);
		final INDArray result1 = mFlux.add(pForce);
		final INDArray result2 = result1.reshape(9, 2).sum(0);
		return result2;
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
	INDArray computeMomentumFlux(final int... indices) {
		final INDArray q1 = getLocalMomentum(indices);
		final INDArray u1 = getLocalSpeed(indices);
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
		final INDArray result = Utils.mmul(result1, SECTIONS).mul(universe.getCellArea());

		result.get(NDArrayIndex.point(1), NDArrayIndex.point(1), NDArrayIndex.all()).assign(0.0);
		return result;
	}

	/**
	 * Returns the pressure force <code>
	 * -(p(ab) - p(22)) n(abc) s(ab)
	 * </code>
	 *
	 * @param indices
	 * @return
	 */
	INDArray computePressureForce(final int... indices) {
		final INDArray p = getLocalPressure(indices);
		final INDArray dp = Utils.cellDifferences(p);
		final INDArray fs = Utils.mmul(NORMALS, dp);
		final INDArray f = Utils.mmul(fs, SECTIONS).mul(-universe.getCellArea());
		return f;
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
	List<int[]> computeReactionSurfaces(final int... indices) {
		final List<int[]> result = computeReactiveSurfaces(indices);
		return result;
	}

	/**
	 * Returns the list of indices of reactive surfaces for a given cell
	 *
	 * @param indices indices of cell
	 * @return the list of indices of reactive surfaces
	 */
	List<int[]> computeReactiveSurfaces(final int... indices) {
		final INDArray map = getLocalConstraints(indices);
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
	 *
	 * @param indices
	 * @return
	 */
	INDArray getLocalConstraints(final int... indices) {
		return Utils.local(padConstraints, indices);
	}

	/**
	 *
	 * @param i
	 * @param j
	 * @return
	 */
	INDArray getLocalEnergy(final int... indices) {
		return Utils.local(padEnergy, indices);
	}

	/**
	 *
	 * @param i
	 * @param j
	 * @return
	 */
	INDArray getLocalMass(final int... indices) {
		return Utils.local(padMass, indices);
	}

	/**
	 *
	 * @param i
	 * @param j
	 * @return
	 */
	INDArray getLocalMomentum(final int... indices) {
		return Utils.local(padMomentum, indices);
	}

	/**
	 *
	 * @param indices
	 * @return
	 */
	INDArray getLocalPressure(final int... indices) {
		return Utils.local(padPressure, indices);
	}

	/**
	 *
	 * @param i
	 * @param j
	 * @return
	 */
	INDArray getLocalSpeed(final int... indices) {
		return Utils.local(padSpeed, indices);
	}

	/**
	 *
	 * @return
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
	 * @return the padConstraints
	 */
	INDArray getPadConstraints() {
		return padConstraints;
	}

	/**
	 * @return the padEnergy
	 */
	INDArray getPadEnergy() {
		return padEnergy;
	}

	/**
	 * @return the padMass
	 */
	INDArray getPadMass() {
		return padMass;
	}

	/**
	 * @return the padMomentum
	 */
	INDArray getPadMomentum() {
		return padMomentum;
	}

}
