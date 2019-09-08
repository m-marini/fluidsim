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

package org.mmarini.fluid.model1;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class UniverseBuilder {

	private static final int[] DEFAULT_SHAPE = new int[] { 80, 80 };

	/**
	 * Create universe builder with default parameters
	 */
	public static UniverseBuilder create() {
		return new UniverseBuilder(DEFAULT_SHAPE);
	}

	private final int[] shape;

	/**
	 * Create a universe builder with given parameters
	 *
	 * @param size universe size
	 */
	public UniverseBuilder(final int[] shape) {
		this.shape = shape;
	}

	/**
	 * Returns the universe built from parameters
	 */
	public Universe build() {
		final INDArray cells = Nd4j.zeros(shape);
		final INDArray relations = Nd4j.zeros(shape);
		return new UniverseImpl(shape, cells, relations);
	}

	/**
	 * Returns the universe builder for a give shape
	 */
	public UniverseBuilder setShape(final int[] shape) {
		return new UniverseBuilder(shape);
	}
}
