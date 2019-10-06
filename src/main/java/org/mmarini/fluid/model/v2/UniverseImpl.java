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

/**
 * @author mmarini
 */
public class UniverseImpl implements Universe {
	private final INDArray density;
	private final INDArray speed;
	private final INDArray temperature;
	private final double cellVolume;
	private final INDArray massConstraints;
	private final double lenght;
	private final double cellArea;
	private final double specificHeatCapacity;
	private final double molecularMass;

	/**
	 * Creates the universe
	 *
	 * @param massConstraints
	 */
	public UniverseImpl(final double length, final INDArray density, final INDArray speed, final INDArray temperature,
			final double molecularMass, final double specificHeatCapacity, final INDArray massConstraints) {
		super();
		assert (density.rank() == 2);
		assert (speed.rank() == 3);
		assert (speed.shape()[0] == density.shape()[0]);
		assert (speed.shape()[1] == density.shape()[1]);
		assert (speed.shape()[2] == 2);
		assert (temperature.equalShapes(density));
		this.density = density;
		this.speed = speed;
		this.temperature = temperature;
		this.lenght = length;
		this.cellArea = length * length;
		this.cellVolume = length * length * length;
		this.massConstraints = massConstraints;
		this.molecularMass = molecularMass;
		this.specificHeatCapacity = specificHeatCapacity;
	}

	@Override
	public double getCellArea() {
		return cellArea;
	}

	/**
	 *
	 */
	@Override
	public double getCellVolume() {
		return cellVolume;
	}

	/**
	 * @see org.mmarini.fluid.model.v2.Universe#getDensity()
	 */
	@Override
	public INDArray getDensity() {
		return density;
	}

	@Override
	public INDArray getMassContraints() {
		return massConstraints;
	}

	@Override
	public double getMolecularMass() {
		return molecularMass;
	}

	@Override
	public double getSpecificHeatCapacity() {
		return specificHeatCapacity;
	}

	/**
	 * @return the speed
	 */
	@Override
	public INDArray getSpeed() {
		return speed;
	}

	/**
	 * @return the energy
	 */
	@Override
	public INDArray getTemperature() {
		return temperature;
	}

	/**
	 *
	 */
	@Override
	public Universe step(final double dt) {
		return this;
	}
}
