/*
 * Utils.java
 *
 * $Id: Utils.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 *
 * 16/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.awt.Dimension;

/**
 * This is an utilities class.
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: Utils.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 * 
 */
public class Utils {
	/**
	 * Transforms the relative coordinates into column index.
	 * 
	 * @param universe
	 *            the universe
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the column index
	 */
	public static int transformToColumn(final Universe universe,
			final double x, final double y) {
		final int gx = transformToX(universe, x, y);
		final int gy = transformToX(universe, x, y);
		final int col = transformToColumn(universe, gx, gy);
		return col;
	}

	/**
	 * Transforms the graphic coordinates into column index.
	 * 
	 * @param universe
	 *            the universe
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the column index
	 */
	public static int transformToColumn(final Universe universe, int x,
			final int y) {
		if ((y % 2) == 1)
			--x;
		final int w = universe.getSize().width;
		return Math.min(Math.max(x / 2, 0), w - 1);
	}

	/**
	 * Transforms the relative coordinates into row index.
	 * 
	 * @param universe
	 *            the universe
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the row index
	 */
	public static int transformToRow(final Universe universe, final double x,
			final double y) {
		final int gx = transformToX(universe, x, y);
		final int gy = transformToY(universe, x, y);
		return transformToRow(universe, gx, gy);
	}

	/**
	 * Transforms the graphic coordinates into row index.
	 * 
	 * @param universe
	 *            the universe
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the row index
	 */
	public static int transformToRow(final Universe universe, final int x,
			final int y) {
		return y;
	}

	/**
	 * Transforms the index cooridnates into x graphic coordinate.
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return the x coordinate
	 */
	public static int transformToX(final int i, final int j) {
		int x = 2 * j;
		if ((i % 2) == 1)
			++x;
		return x;
	}

	/**
	 * Transform relative coordinates to x graphic coordinate
	 * 
	 * @param universe
	 *            the universe
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the x coordinate
	 */
	public static int transformToX(final Universe universe, final double x,
			final double y) {
		final Dimension size = universe.getSize();
		final int w = size.width;
		final int dw = w * 2 - 1;
		return (int) Math.round(x * dw);
	}

	/**
	 * Transforms the index cooridnates into y graphic coordinate.
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return the y coordinate
	 */
	public static int transformToY(final int i, final int j) {
		return i;
	}

	/**
	 * Transform relative coordinates to y graphic coordinate
	 * 
	 * @param universe
	 *            the universe
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the y coordinate
	 */
	public static int transformToY(final Universe universe, final double x,
			final double y) {
		final Dimension size = universe.getSize();
		final int dh = size.height - 1;
		return (int) Math.round(y * dh);
	}
}
