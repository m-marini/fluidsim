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
	private static final double LENGTH = 0.01;

	/**
	 * Create universe builder with default parameters
	 */
	public static UniverseBuilder create() {
		return new UniverseBuilder(DEFAULT_SHAPE);
	}

	private final long[] shape;

	/**
	 * Create a universe builder with given parameters
	 *
	 * @param massCostraint
	 *
	 * @param siz           universe size
	 */
	public UniverseBuilder(final long[] shape) {
		this.shape = shape;
	}

	/**
	 * Returns the universe built from parameters
	 */
	public Universe build() {
		final INDArray density = Nd4j.randn(DataType.DOUBLE, shape).mul(ISA_DENSITY / 100).add(ISA_DENSITY);
//		density.putScalar(new int[] { 40, 40, 0 }, 4);
		final INDArray speed = Nd4j.zeros(DataType.DOUBLE, new long[] { shape[0], shape[1], 2 });
		for (int i = 0; i < shape[0]; i++) {
			for (int j = 0; j < shape[1]; j++) {
				speed.putScalar(new long[] { i, j, 0 }, SPEED);
			}
		}
		final INDArray temperature = Nd4j.ones(DataType.DOUBLE, shape).mul(ISA_TEMPERATURE);
		final INDArray massCostraints = Nd4j.zeros(DataType.DOUBLE, shape);
		return new UniverseImpl(LENGTH, density, speed, temperature, ISA_SPECIFIC_HEAT_CAPACITY, ISA_MOLECULAR_MASS_,
				massCostraints);
	}

	/**
	 * Returns the universe builder for a give shape
	 */
	public UniverseBuilder setShape(final long[] shape) {
		return new UniverseBuilder(shape);
	}
}
