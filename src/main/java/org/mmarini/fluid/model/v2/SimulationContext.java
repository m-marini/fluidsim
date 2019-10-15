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
import org.nd4j.linalg.api.ops.impl.transforms.Pad.Mode;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Stores the temporary data for simulation
 *
 * @author mmarini
 *
 */
public class SimulationContext implements Constants {
	private static final int[][] VECTOR_PAD_WIDTHS = new int[][] { { 1, 1 }, { 1, 1 }, { 0, 0 } };

	private final Universe universe;
	private final INDArray mass;
	private final INDArray momentum;
	private final INDArray padMass;
	private final INDArray padMomentum;
	private final INDArray padEnergy;
	private final INDArray padSpeed;
	private final INDArray padPressure;
	private final INDArray padConstraints;
	private final double interval;

	/**
	 *
	 * @param universe
	 * @param interval TODO
	 * @param interval
	 */
	public SimulationContext(final Universe universe, final double interval) {
		super();
		this.universe = universe;
		this.interval = interval;
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

	public double getCellArea() {
		return universe.getCellArea();
	}

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

	/**
	 * @return the padPressure
	 */
	INDArray getPadPressure() {
		return padPressure;
	}

	/**
	 * @return the padSpeed
	 */
	INDArray getPadSpeed() {
		return padSpeed;
	}

}
