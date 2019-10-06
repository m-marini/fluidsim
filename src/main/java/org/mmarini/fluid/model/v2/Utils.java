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
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

/**
 * @author mmarini
 *
 */
public class Utils {

// Casistiche operazioni su matrice:
// - matrice scalare +/- matrice scalare =>	 * a.add(b) / a.sub(b)
// - matrice vettore +/- matrice vettore => a.add(b) /a.sub(b)
// - matrice scalare * matrice scalare => a.mul(b)
// - matrice vettore * matrice scalare => Utils.mmul(a, b)
// - matrice vettore * matrice vettore => prodotto scalare, Utils.mmul(a, b)
//
// Casistice da verificare se necessarie:
// - matrice vettore +/- matrice scalare
// - matrice vettore x matrice vettore => prodotto tensoriale
// - matrice tensore * matrice tensore => prodotto interno
// - matrice tensore x matrice tensore => prodotto esterno

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
	 *
	 * @param a
	 * @return
	 */
	public static INDArray cellAddStamp(final INDArray a) {
		final INDArray a22 = Nd4j.ones(3, 3, 2);
		a22.get(NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.point(0)).assign(a.getDouble(1, 1, 0));
		a22.get(NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.point(1)).assign(a.getDouble(1, 1, 1));
		final INDArray result = a.add(a22);
		result.get(NDArrayIndex.point(1), NDArrayIndex.point(1), NDArrayIndex.all()).assign(0.0);
		return result;
	}

	/**
	 *
	 * @param a
	 * @return
	 */
	public static INDArray cellDifferences(final INDArray a) {
		final INDArray a22 = Nd4j.ones(3, 3).muli(a.getDouble(1, 1));
		final INDArray result = a.sub(a22);
		return result;
	}

	/**
	 * Returns the submatrix of values with the adjacent cells
	 *
	 * @param values  the padded input matrix
	 * @param indices the indices
	 * @return the matrix
	 */
	public static INDArray local(final INDArray values, final int... indices) {
		return values.get(NDArrayIndex.interval(indices[0], indices[0] + 3),
				NDArrayIndex.interval(indices[1], indices[1] + 3));
	}

	/**
	 * Returns the product between a vector matrix and a scalar matrix or vector
	 * matrix
	 *
	 * @param a vector grid
	 * @param b scalar or vector grid
	 * @return the product
	 */
	public static INDArray mmul(final INDArray a, final INDArray b) {
		assert (a.rank() == 3);
		assert (b.rank() == 2 || b.rank() == 3);
		assert (a.size(0) == b.size(0));
		assert (a.size(1) == b.size(1));
		if (b.rank() == 2) {
			final INDArray c = a.mul(suffixBroadcast(b, Arrays.copyOfRange(a.shape(), 2, a.rank())));
			return c;
		} else {
			assert (a.size(2) == b.size(2));
			final INDArray c = a.mul(b).sum(2);
			return c;
		}
	}

	/**
	 * Returns the tensor multiplication and reducing n dimensions.
	 * <p>
	 * The reduction occurs on the last n dimension of a tensor and first n
	 * dimension of b tensor.
	 * </p>
	 *
	 * @param a
	 * @param b
	 * @param nDims
	 * @return
	 */
	public static INDArray mul(final INDArray a, final INDArray b, final int nDims) {
		assert (a.rank() >= nDims);
		assert (b.rank() >= nDims);
		for (int i = 0; i < nDims; i++) {
			assert (a.size(a.rank() - i - 1) == b.size(i));
		}
		return mulUnsafe(a, b, nDims);
	}

	/**
	 *
	 * @param a
	 * @param b
	 * @param nDims
	 * @return
	 */
	public static INDArray mulUnsafe(final INDArray a, final INDArray b, final int nDims) {
		final long[] as = a.shape();
		final long[] bs = b.shape();
		final long[] headShape = nDims > 0 ? Arrays.copyOf(as, a.rank() - nDims) : as;
		final long[] tailShape = nDims > 0 ? Arrays.copyOfRange(bs, nDims, b.rank()) : bs;
		final long[] midShape = nDims > 0 ? Arrays.copyOf(bs, nDims) : new long[0];
		final long n = Arrays.stream(headShape).sum();
		final long m = Arrays.stream(tailShape).sum();
		final long p = Arrays.stream(midShape).sum();

		final long[] resultShape = catShape(headShape, tailShape);
		@SuppressWarnings("resource")
		final INDArray a1 = n > 0 ? p > 0 ? a.reshape(n, p) : a.reshape(n, 1) : a.reshape(1, p);
		final INDArray b1 = m > 0 ? p > 0 ? b.reshape(p, m) : b.reshape(1, m) : b.reshape(p, 1);
		final INDArray c = a1.mmul(b1);
		final INDArray c1 = c.reshape(resultShape);
		return c1;
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
	 *
	 * @param permute
	 * @return
	 */
	public static int[] reversePermute(final int... permute) {
		final int[] result = new int[permute.length];
		for (int i = 0; i < permute.length; i++) {
			result[permute[i]] = i;
		}
		return result;
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

}
