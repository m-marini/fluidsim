/*
 * CellShape.java
 *
 * $Id: CellShape.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 * 07/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing1;

import java.awt.Graphics;

/**
 * The CellShape draws the structure of a cell.
 * <p>
 * Depending on the graphical size of the cell it draws a rectangular shape for
 * small cells and an exagonal shape for the other ones.
 * 
 * <pre>
 *   - 3 -
 * 4       2
 * |   +   |
 * 5       1
 *   - 0 -
 * </pre>
 * 
 * @author US00852
 * @version $Id: CellShape.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 * 
 */
public class CellShape {
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
	private static final int XOFFSET = (int) Math.round(RADIUS
			* Math.cos(Math.PI / 6));

	/**
	 * The y offset of the template shape
	 */
	private static final int YOFFSET = RADIUS;

	/**
	 * The width of the template shape
	 */
	private static final int WITDH = (int) Math.round(2 * RADIUS
			* Math.cos(Math.PI / 6));

	/**
	 * The heigh of the template shape
	 */
	private static final int HEIGHT = 2 * RADIUS;

	private final int[][] template;
	private final int[][] points;

	/**
	 * Creates the Shape.
	 * <p>
	 * Calculates the positions of point of the exagonal shape.
	 * </p>
	 */
	public CellShape() {
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
	 * @param gr
	 *            the graphics context
	 * @param x
	 *            the x position (left)
	 * @param y
	 *            the y position (bottom)
	 * @param w
	 *            the width of the cell
	 * @param h
	 *            the heigh of the cell
	 */
	public void draw(final Graphics gr, final int x, final int y, final int w,
			final int h) {
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
	 * @param n
	 *            the numerator
	 * @param d
	 *            the denominator
	 * @return the value
	 */
	private int roundDiv(final int n, final int d) {
		return (n + n + d) / (2 * d);
	}

}
