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

/**
 * @author mmarini
 */
public class UniverseImpl implements Universe {

	private final int[] shape;
	private final INDArray cells;
	private final INDArray relations;

	/**
	 * Creates the universe
	 *
	 * @param size
	 * @param cells
	 * @param relations
	 */
	public UniverseImpl(final int[] shape, final INDArray cells, final INDArray relations) {
		super();
		this.shape = shape;
		this.cells = cells;
		this.relations = relations;
	}

	/**
	 * @see org.mmarini.fluid.model1.Universe#getCells()
	 */
	@Override
	public INDArray getCells() {
		return cells;
	}

	/**
	 * @see org.mmarini.fluid.model1.Universe#getRelations()
	 */
	@Override
	public INDArray getRelations() {
		return relations;
	}

	/**
	 * @see org.mmarini.fluid.model1.Universe#getSize()
	 */
	@Override
	public int[] getShape() {
		return shape;
	}

	/**
	 * @see org.mmarini.fluid.model1.Universe#simulate(double)
	 */
	@Override
	public Universe simulate(final double time) {
		throw new Error("Not implemented");
	}
}