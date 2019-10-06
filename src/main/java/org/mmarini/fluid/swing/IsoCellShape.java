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
public class IsoCellShape implements CellShape {
	/**
	 * The minimum size of the cell to draw the exagonal shape
	 */
	private static final int MIN_SIZE = 4;

	/**
	 * The number of edges of the cell poligonal shape
	 */
	private static final int EDGES_COUNT = 6;

	/**
	 * The radius of the template shape structure
	 */
	private static final int RADIUS = 10000;

	/**
	 * The x offset of the template shape
	 */
	private static final int XOFFSET = (int) Math.round(RADIUS * Math.cos(Math.PI / 6));

	/**
	 * The y offset of the template shape
	 */
	private static final int YOFFSET = RADIUS;

	/**
	 * The width of the template shape
	 */
	private static final int WITDH = (int) Math.round(2 * RADIUS * Math.cos(Math.PI / 6));

	/**
	 * The heigh of the template shape
	 */
	private static final int HEIGHT = 2 * RADIUS;

	private static final IsoCellShape instance = new IsoCellShape();

	public static IsoCellShape getInstance() {
		return instance;
	}

	private final int[][] template;

	private final int[][] points;

	/**
	 * Creates the Shape.
	 * <p>
	 * Calculates the positions of point of the exagonal shape.
	 * </p>
	 */
	protected IsoCellShape() {
		template = new int[2][EDGES_COUNT];
		points = new int[2][EDGES_COUNT];

		for (int i = 0; i < EDGES_COUNT; ++i) {
			final double a = Math.PI * 2 * i / EDGES_COUNT;
			template[0][i] = (int) Math.round(Math.sin(a) * RADIUS + XOFFSET);
			template[1][i] = (int) Math.round(Math.cos(a) * RADIUS + YOFFSET);
		}
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
		if (w <= MIN_SIZE || h <= MIN_SIZE) {
			gr.fillRect(x, y, w, h);
		} else {
			final int[] ptsx = points[0];
			final int[] ptsy = points[1];
			final int[] templx = template[0];
			final int[] temply = template[1];
			final int w1 = (w - 1);
			final int h1 = (h - 1);
			for (int i = 0; i < EDGES_COUNT; ++i) {
				ptsx[i] = x + roundDiv(templx[i] * w1, WITDH);
				ptsy[i] = y + roundDiv(temply[i] * h1, HEIGHT);
			}
			gr.fillPolygon(ptsx, ptsy, EDGES_COUNT);
		}
	}

	/**
	 * Calculats the integer nearer to the value n / d.
	 *
	 * @param n the numerator
	 * @param d the denominator
	 * @return the value
	 */
	private int roundDiv(final int n, final int d) {
		return (n + n + d) / (2 * d);
	}

}
