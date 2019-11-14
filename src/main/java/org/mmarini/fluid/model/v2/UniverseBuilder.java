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

import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class UniverseBuilder implements Constants {

	private static final long[] DEFAULT_SHAPE = new long[] { 80, 80 };
	private static final double DEFAULT_LENGTH = 0.01;

	/**
	 *
	 * @return
	 */
	public static UniverseBuilder createISA() {
		return new UniverseBuilder(DEFAULT_SHAPE, DEFAULT_LENGTH, ISA_TEMPERATURE, ISA_DENSITY, ISA_MOLECULAR_MASS);
	}

	private final long[] shape;
	private final double length;
	private final double molecularMass;
	private final INDArray density;
	private final double temperature;

	/**
	 * Create a universe builder with given parameters
	 *
	 * @param shape
	 * @param length
	 * @param molecularMass
	 * @param density
	 * @param temperature
	 */
	protected UniverseBuilder(final long[] shape, final double length, final double temperature, final double density,
			final double molecularMass) {
		this.shape = shape;
		this.length = length;
		this.density = Nd4j.ones(DataType.DOUBLE, shape).mul(density);
		this.molecularMass = molecularMass;
		this.temperature = temperature;
	}

	/**
	 * Returns the universe built from parameters
	 */
	public Universe build() {
		final INDArray speed = Nd4j.zeros(DataType.DOUBLE, new long[] { shape[0], shape[1], 2 });
		final INDArray mu = Nd4j.ones(DataType.DOUBLE, shape);
		return new UniverseImpl(length, density, speed, temperature, molecularMass, mu);
	}

	/**
	 * Returns the universe builder for a give length
	 */
	public UniverseBuilder length(final double length) {
		return new UniverseBuilder(shape, length, length, length, molecularMass);
	}

	/**
	 * Returns the universe builder for a give shape
	 */
	public UniverseBuilder shape(final long[] shape) {
		return new UniverseBuilder(shape, length, length, length, molecularMass);
	}
}
