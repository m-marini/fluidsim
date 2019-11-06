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

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

/**
 * Stores the temporary data for simulation
 *
 * @author mmarini
 *
 */
public class SimulationContext implements Constants {
	private static final INDArray NORTH = Nd4j.create(new float[] { 0, -1 });
	private static final INDArray SOUTH = Nd4j.create(new double[] { 0, 1 });
	private static final INDArray EAST = Nd4j.create(new double[] { -1, 0 });
	private static final INDArray WEST = Nd4j.create(new double[] { 1, 0 });

	/**
	 *
	 * @param cells
	 * @return
	 */
	static private INDArray buildHSurface(final INDArray cells) {
		final long[] sh = cells.shape();
		final long n = sh[0];
		final long m = sh[1];
		final INDArray a = cells.get(NDArrayIndex.interval(0, n - 1), NDArrayIndex.interval(0, m));
		final INDArray b = cells.get(NDArrayIndex.interval(1, n), NDArrayIndex.interval(0, m));
		final INDArray c = a.add(b).mul(0.5);
		final INDArray hd = Nd4j.zeros(n + 1, m);
		hd.get(NDArrayIndex.interval(1, n), NDArrayIndex.interval(0, m)).assign(c);
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
	 * @param cells
	 * @return
	 */
	static private INDArray buildVSurface(final INDArray cells) {
		final long[] sh = cells.shape();
		final long n = sh[0];
		final long m = sh[1];
		final INDArray vd = Nd4j.zeros(n, m + 1);

		final INDArray a = cells.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(0, m - 1));
		final INDArray b = cells.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(1, m));
		final INDArray c = a.add(b).mul(0.5);
		vd.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(1, m)).assign(c);
		return vd;
	}

	private final Universe universe;
	private final INDArray mass;
	private final INDArray momentum;
	private final double interval;
	private final INDArray energy;
	private final INDArray pressure;
	private final INDArray hDensity;
	private final INDArray vDensity;
	private final INDArray vEnergy;
	private final INDArray hEnergy;
	private final INDArray hMomentum;
	private final INDArray vMomentum;
	private final INDArray vMassFlux;
	private final INDArray vSpeed;
	private final INDArray hSpeed;
	private final INDArray hMassFlux;
	private final INDArray deltaRho;

	/**
	 *
	 * @param universe
	 * @param interval
	 */
	public SimulationContext(final Universe universe, final double interval) {
		super();
		this.universe = universe;
		this.interval = interval;
		this.mass = universe.getDensity().mul(universe.getCellVolume());
		this.momentum = Utils.mmul(universe.getSpeed(), mass);
		this.energy = universe.getTemperature().mul(mass).mul(universe.getSpecificHeatCapacity());
		this.pressure = universe.getTemperature().mul(mass)
				.mul(R / universe.getCellVolume() / universe.getMolecularMass());
		this.hDensity = buildHSurface(universe.getDensity());
		this.vDensity = buildVSurface(universe.getDensity());
		this.hEnergy = buildHSurface(this.energy);
		this.vEnergy = buildVSurface(this.energy);
		this.hSpeed = buildHVector(universe.getSpeed());
		this.vSpeed = buildVVector(universe.getSpeed());
		this.hMomentum = buildHVector(momentum);
		this.vMomentum = buildVVector(momentum);
		this.vMassFlux = Utils.mmul(vSpeed, vDensity).muli(universe.getCellArea());
		this.hMassFlux = Utils.mmul(hSpeed, hDensity).muli(universe.getCellArea());
		this.deltaRho = buildDeltaRho();
	}

	/**
	 * 
	 * @return
	 */
	private INDArray buildDeltaRho() {
		final long[] sh = mass.shape();
		final long n = sh[0];
		final long m = sh[1];
		final INDArray north = hMassFlux.get(NDArrayIndex.interval(0, n), NDArrayIndex.all());
		final INDArray south = hMassFlux.get(NDArrayIndex.interval(1, n + 1), NDArrayIndex.all());
		final INDArray east = vMassFlux.get(NDArrayIndex.all(), NDArrayIndex.interval(0, m));
		final INDArray west = vMassFlux.get(NDArrayIndex.all(), NDArrayIndex.interval(1, m + 1));
		final INDArray northDelta = Utils.mmul(north, Utils.prefixBroadcast(NORTH, n, m));
		final INDArray southDelta = Utils.mmul(south, Utils.prefixBroadcast(SOUTH, n, m));
		final INDArray eastDelta = Utils.mmul(east, Utils.prefixBroadcast(EAST, n, m));
		final INDArray westDelta = Utils.mmul(west, Utils.prefixBroadcast(WEST, n, m));
		final INDArray result = northDelta.add(southDelta).addi(eastDelta).addi(westDelta)
				.muli(-interval / universe.getCellVolume());
		return result;
	}

	/**
	 * 
	 * @param cells
	 * @return
	 */
	private INDArray buildVVector(final INDArray cells) {
		final long[] sh = cells.shape();
		final long n = sh[0];
		final long m = sh[1];
		final INDArray a = cells.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(0, m - 1), NDArrayIndex.all());
		final INDArray b = cells.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(1, m), NDArrayIndex.all());
		final INDArray c = a.add(b).mul(0.5);
		final INDArray result = Nd4j.zeros(n, m + 1, 2);
		result.get(NDArrayIndex.interval(0, n), NDArrayIndex.interval(1, m), NDArrayIndex.all()).assign(c);
		return result;
	}

	/**
	 *
	 * @return
	 */
	public double getCellArea() {
		return universe.getCellArea();
	}

	/**
	 * 
	 * @return
	 */
	INDArray getDeltaRho() {
		return deltaRho;
	}

	/**
	 *
	 * @return
	 */
	INDArray getEnergy() {
		return energy;
	}

	/**
	 *
	 * @return
	 */
	INDArray getHDensity() {
		return hDensity;
	}

	INDArray getHEnergy() {
		return hEnergy;
	}

	/**
	 * 
	 * @return
	 */
	INDArray getHMassFlux() {
		return hMassFlux;
	}

	/**
	 * 
	 * @return
	 */
	INDArray getHMomentum() {
		return hMomentum;
	}

	/**
	 * 
	 * @return
	 */
	INDArray getHSpeed() {
		return hSpeed;
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
	 * @return the momentum
	 */
	INDArray getMomentum() {
		return momentum;
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

	/**
	 *
	 * @return
	 */
	INDArray getVDensity() {
		return vDensity;
	}

	/**
	 * @return the vEnergy
	 */
	INDArray getVEnergy() {
		return vEnergy;
	}

	/**
	 * 
	 * @return
	 */
	INDArray getVMassFlux() {
		return vMassFlux;
	}

	/**
	 * 
	 * @return
	 */
	INDArray getVMomentum() {
		return vMomentum;
	}

	/**
	 * 
	 * @return
	 */
	INDArray getVSpeed() {
		return vSpeed;
	}
}
