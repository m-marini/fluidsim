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
package org.mmarini.fluid.swing;

import java.awt.Graphics;

/**
 * The CellShape draws the structure of a cell.
 * <p>
 * Depending on the graphical size of the cell it draws a rectangular shape for
 * small cells and an exagonal shape for the other ones.
 * </p>
 *
 * @version $Id: CellShape.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 */
public class RectangularCellShape implements CellShape {
	private static final RectangularCellShape instance = new RectangularCellShape();

	public static RectangularCellShape getInstance() {
		return instance;
	}

	/**
	 * Creates the Shape.
	 * <p>
	 * Calculates the positions of point of the regtangular shape.
	 * </p>
	 */
	protected RectangularCellShape() {
	}

	/**
	 * Draws the shape
	 *
	 * @param gr the graphics context
	 * @param x  the x position (left)
	 * @param y  the y position (bottom)
	 * @param w  the width of the cell
	 * @param h  the heigh of the cell
	 */
	@Override

	public void draw(final Graphics gr, final int x, final int y, final int w, final int h) {
		gr.fillRect(x, y, w, h);
	}
}
