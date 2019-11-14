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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

import io.reactivex.rxjava3.core.Single;

/**
 * Stores the temporary data for simulation
 *
 * @author mmarini
 *
 */
public class SimulationContext implements Constants {
	private static final INDArray NORTH = Nd4j.create(new float[] { 0, -1 });
	private static final INDArray SOUTH = Nd4j.create(new double[] { 0, 1 });
	private static final INDArray EAST = Nd4j.create(new double[] { 1, 0 });
	private static final INDArray WEST = Nd4j.create(new double[] { -1, 0 });
	private static final List<INDArray> NORMALS = Arrays.asList(NORTH, EAST, SOUTH, WEST);

	/**
	 *
	 * @param cells
	 * @return
	 */
//	static private INDArray buildHConstraints(final INDArray cells) {
//		final long[] sh = cells.shape();
//		final long n = sh[0];
//		final long m = sh[1];
//		final INDArray a = cells.get(NDArrayIndex.interval(0, n - 1), NDArrayIndex.interval(0, m));
//		final INDArray b = cells.get(NDArrayIndex.interval(1, n), NDArrayIndex.interval(0, m));
//		final INDArray c = a.mul(b);
//		final INDArray hd = Nd4j.zeros(n + 1, m);
//		hd.get(NDArrayIndex.interval(1, n), NDArrayIndex.interval(0, m)).assign(c);
//		return hd;
//	}

	/**
	 *
	 * @param cells
	 * @return
	 */
	static private INDArray buildHSurface(final INDArray cells) {
		final long[] sh = cells.shape();
		final long n = sh[0];
		final long m = sh[1];
		final INDArray a = cells.get(NDArrayIndex.interval(0, n - 1));
		final INDArray b = cells.get(NDArrayIndex.interval(1, n));
		final INDArray c = a.add(b).mul(0.5);
		final INDArray north = cells.get(NDArrayIndex.point(0));
		final INDArray south = cells.get(NDArrayIndex.point(n - 1));
		final INDArray hd = Nd4j.concat(0, north.ravel(), c.ravel(), south.ravel()).reshape(n + 1, m);
		return hd;
	}

	/**
	 *
	 * @param cells
	 * @return
	 */
	static private INDArray buildHVector(final INDArray cells) {
		final long[] sh = cells.shape();
		final long n = sh[0];
		final long m = sh[1];
		final INDArray a = cells.get(NDArrayIndex.interval(0, n - 1), NDArrayIndex.interval(0, m), NDArrayIndex.all());
		final INDArray b = cells.get(NDArrayIndex.interval(1, n), NDArrayIndex.interval(0, m), NDArrayIndex.all());
		final INDArray c = a.add(b).mul(0.5);
		final INDArray result = Nd4j.zeros(n + 1, m, 2);
		result.get(NDArrayIndex.interval(1, n), NDArrayIndex.interval(0, m), NDArrayIndex.all()).assign(c);
		return result;
	}

	/**
	 *
	 * @param density
	 * @param volume
	 * @return
	 */
	static private INDArray buildMass(final INDArray density, final double volume) {
		return density.mul(volume);
	}

	/**
	 *
	 * @param density
	 * @param temperature
	 * @param molecularMass
	 * @return
	 */
	static private INDArray buildPressure(final INDArray density, final double temperature,
			final double molecularMass) {
		return density.mul(R * temperature / molecularMass);
	}

	/**
	 *
	 * @param cells
	 * @return
	 */
//	static private INDArray buildVConstraints(final INDArray cells) {
//		final long[] sh = cells.shape();
//		final long n = sh[0];
//		final long m = sh[1];
//		final INDArray vd = Nd4j.zeros(n, m + 1);
//		final INDArray a = cells.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(0, m - 1));
//		final INDArray b = cells.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(1, m));
//		final INDArray c = a.mul(b);
//		vd.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(1, m)).assign(c);
//		return vd;
//	}

	/**
	 *
	 * @param cells
	 * @return
	 */
	static private INDArray buildVSurface(final INDArray cells) {
		final long[] sh = cells.shape();
		final long n = sh[0];
		final long m = sh[1];
		final INDArray a = cells.get(NDArrayIndex.all(), NDArrayIndex.interval(0, m - 1));
		final INDArray b = cells.get(NDArrayIndex.all(), NDArrayIndex.interval(1, m));
		final INDArray c = a.add(b).mul(0.5);
		final INDArray left = cells.get(NDArrayIndex.all(), NDArrayIndex.point(0));
		final INDArray right = cells.get(NDArrayIndex.all(), NDArrayIndex.point(n - 1));
		final INDArray vd = Nd4j.concat(0, left.ravel(), c.transpose().ravel(), right.ravel()).reshape(m + 1, n)
				.transpose();
		return vd;
	}

	/**
	 *
	 * @param cells
	 * @return
	 */
	private static INDArray buildVVector(final INDArray cells) {
		final long[] sh = cells.shape();
		final long n = sh[0];
		final long m = sh[1];
		final INDArray a = cells.get(NDArrayIndex.all(), NDArrayIndex.interval(0, m - 1), NDArrayIndex.all());
		final INDArray b = cells.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(1, m), NDArrayIndex.all());
		final INDArray c = a.add(b).mul(0.5);
		final INDArray result = Nd4j.zeros(n, m + 1, 2);
		result.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(1, m), NDArrayIndex.all()).assign(c);
		return result;
	}

	/**
	 *
	 * @param dim
	 * @return
	 */
	static private INDArray normals(final long... dim) {
		final List<INDArray> x = NORMALS.stream().map(n -> Utils.prefixBroadcast(n, dim)).collect(Collectors.toList());
		final long[] shape = new long[dim.length + 2];
		System.arraycopy(dim, 0, shape, 1, dim.length);
		shape[0] = NORMALS.size();
		shape[dim.length + 1] = 2;
		final INDArray result = Nd4j.create(x, shape);
		return result;
	}

	/**
	 *
	 * @param horizontal
	 * @param vertical
	 * @return
	 */
	static private INDArray surfaces(final INDArray horizontal, final INDArray vertical) {
		final long n = vertical.size(0);
		final long m = horizontal.size(1);
		final INDArray nSpeed = horizontal.get(NDArrayIndex.interval(0, n));
		final INDArray sSpeed = horizontal.get(NDArrayIndex.interval(1, n + 1));
		final INDArray wSpeed = vertical.get(NDArrayIndex.all(), NDArrayIndex.interval(0, m));
		final INDArray eSpeed = vertical.get(NDArrayIndex.all(), NDArrayIndex.interval(1, m + 1));
		final long[] shape = nSpeed.shape();
		final long[] newShape = new long[shape.length + 1];
		System.arraycopy(shape, 0, newShape, 1, shape.length);
		newShape[0] = 4;
		final INDArray result = Nd4j.create(Arrays.asList(nSpeed, eSpeed, sSpeed, wSpeed), newShape);
		return result;
	}

	private final Universe universe;
	private final INDArray mass;
	private final double interval;
	private final INDArray pressure;
	private final INDArray norms;
	private final INDArray sSpeed;
	private final INDArray sPressure;
	private final INDArray sFlux;

	/**
	 *
	 * @param universe
	 * @param interval
	 */
	public SimulationContext(final Universe universe, final double interval) {
		super();
		this.universe = universe;
		this.interval = interval;
		this.mass = buildMass(universe.getDensity(), universe.getCellVolume());
		this.pressure = buildPressure(universe.getDensity(), universe.getTemperature(), universe.getMolecularMass());
		this.norms = normals(universe.getDensity().shape());
		final INDArray hDensity = buildHDensity();
		final INDArray vDensity = buildVDensity();
		final INDArray hSpeed = buildHSpeed();
		final INDArray vSpeed = buildVSpeed();
		this.sSpeed = surfaces(hSpeed, vSpeed);

//		final INDArray mu = universe.getMu();
//		this.hFree = buildHConstraints(mu);
//		this.vFree = buildVConstraints(mu);

		final INDArray hPressure = buildPressure(hDensity, this.universe.getTemperature(),
				this.universe.getMolecularMass());
		final INDArray vPressure = buildPressure(vDensity, universe.getTemperature(), universe.getMolecularMass());
		sPressure = surfaces(hPressure, vPressure);

		final INDArray hFlux = Utils.vsmul(hSpeed, hDensity);
		final INDArray vFlux = Utils.vsmul(vSpeed, vDensity);
		this.sFlux = surfaces(hFlux, vFlux);
	}

	/**
	 *
	 * @return
	 */
	public Single<Universe> build() {
		final INDArray dRho = computeDeltaRho();
		final INDArray density = universe.getDensity().add(dRho);
		final INDArray speed = universe.getSpeed().add(computeDeltaSpeed());
		final double temperature = universe.getTemperature();
		final double length = universe.getLength();
		final double molecularMass = universe.getMolecularMass();
		final INDArray mu = universe.getMu();
		return Single.just(new UniverseImpl(length, density, speed, temperature, molecularMass, mu));
	}

	/**
	 *
	 * @return
	 */
	INDArray buildHDensity() {
		return buildHSurface(universe.getDensity());
	}

	/**
	 * @return
	 */
	INDArray buildHSpeed() {
		return buildHVector(this.universe.getSpeed());
	}

	/**
	 *
	 * @return
	 */
	INDArray buildVDensity() {
		return buildVSurface(universe.getDensity());
	}

	/**
	 * @return
	 */
	INDArray buildVSpeed() {
		return buildVVector(this.universe.getSpeed());
	}

	/**
	 *
	 * @return
	 */
	INDArray computeDeltaRho() {
		final INDArray delta = Utils.vvmul(sFlux, norms);
		final INDArray result = delta.sum(0).muli(-interval * universe.getCellArea() / universe.getCellVolume());
		return result;
	}

	/**
	 *
	 * @return
	 */
	INDArray computeDeltaSpeed() {
		final INDArray cellFlux = computeMomentFlux();
		final INDArray cellForce = computePressureForce();
		final INDArray delta = cellFlux.add(cellForce);
		final INDArray cellDensity = Utils.suffixBroadcast(universe.getDensity(), 2);
		final INDArray ratio = delta.div(cellDensity);
		final INDArray result = ratio.mul(interval * universe.getCellArea() / universe.getCellVolume());
		return result;
	}

	/**
	 *
	 * @return
	 */
	INDArray computeMomentFlux() {
		final INDArray nSpeed = Utils.vvmul(sSpeed, norms);
		final INDArray flux = Utils.vsmul(sFlux, nSpeed);
		final INDArray result = flux.sum(0).negi();
		return result;
	}

	/**
	 *
	 * @return
	 */
	INDArray computePower() {
		final INDArray nSpeed = Utils.vvmul(sSpeed, norms);
		final INDArray power = sPressure.mul(nSpeed);
		final INDArray result = power.sum(0).negi();
		return result;
	}

	/**
	 *
	 * @return
	 */
	INDArray computePressureForce() {
		final INDArray force = Utils.vsmul(norms, sPressure);
		final INDArray cellForce = force.sum(0).negi();
		return cellForce;
	}

	/**
	 *
	 */
	/**
	 * @return the interval
	 */
	double getInterval() {
		return interval;
	}

	/**
	 *
	 * @return
	 */
	INDArray getMass() {
		return mass;
	}

	/**
	 *
	 * @return
	 */
	INDArray getPressure() {
		return pressure;
	}

	/**
	 *
	 * @return
	 */
	Universe getUniverse() {
		return universe;
	}
}
