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

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * @author mmarini
 *
 */
public class Utils {

	/**
	 *
	 * @param shapes
	 * @return
	 */
	public static long[] catShape(final long[]... shapes) {
		final int n = Arrays.stream(shapes).mapToInt(shape -> shape.length).sum();
		final long[] result = new long[n];
		int idx = 0;
		for (final long[] shape : shapes) {
			System.arraycopy(shape, 0, result, idx, shape.length);
			idx += shape.length;
		}
		return result;
	}

	/**
	 * Broadcasts a tensor by prefixing new dimensions if a is i1 x ... x in tensor
	 * the result is i1 x ... x in x j1 x ... x jm tensor
	 *
	 * @param a
	 * @param newDimensions
	 * @return
	 */
	public static INDArray prefixBroadcast(final INDArray a, final long... newDimensions) {
		final long[] aShape = a.shape();
		final long m = Arrays.stream(newDimensions).reduce(1L, (ac, b) -> ac * b);
		final long[] newShape = catShape(newDimensions, aShape);

		final INDArray a1 = a.ravel();
		final INDArray a2 = a1.broadcast(m, a1.columns());
		final INDArray a3 = a2.reshape(newShape);
		return a3;
	}

	/**
	 * Broadcasts a tensor by suffixing new dimensions if a is i1 x ... x in tensor
	 * the result is i1 x ... x in x j1 x ... x jm tensor
	 *
	 * @param a
	 * @param newDimensions
	 * @return
	 */
	public static INDArray suffixBroadcast(final INDArray a, final long... newDimensions) {
		final long[] aShape = a.shape();
		final long m = Arrays.stream(newDimensions).reduce(1L, (ac, b) -> ac * b);
		final long[] newShape = catShape(aShape, newDimensions);

		final INDArray a1 = a.ravel();
		final INDArray a2 = a1.broadcast(m, a1.columns());
		final INDArray a3 = a2.transpose().reshape(newShape);
		return a3;
	}

	/**
	 * Returns the product between a vector tensor and a scalar tensor
	 *
	 * @param a vector grid
	 * @param b scalar or vector grid
	 * @return the product
	 */
	public static INDArray vsmul(final INDArray vect, final INDArray scalar) {
		assert (vect.rank() == scalar.rank() + 1);
		for (int i = 0; i < scalar.rank(); ++i) {
			assert (vect.size(i) == scalar.size(i));
		}
		final INDArray c = vect.mul(suffixBroadcast(scalar, 2));
		return c;
	}

	/**
	 * Returns the product between two vector tensors
	 *
	 * @param a vector grid
	 * @param b vector grid
	 * @return the product
	 */
	public static INDArray vvmul(final INDArray a, final INDArray b) {
		assert (a.equalShapes(b));
		final INDArray c = a.mul(b).sum(a.rank() - 1);
		return c;
	}
}
