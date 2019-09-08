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
	public static int transformToColumn(Universe universe, double x, double y) {
		int gx = transformToX(universe, x, y);
		int gy = transformToX(universe, x, y);
		int col = transformToColumn(universe, gx, gy);
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
	public static int transformToColumn(Universe universe, int x, int y) {
		if ((y % 2) == 1)
			--x;
		int w = universe.getSize().width;
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
	public static int transformToRow(Universe universe, double x, double y) {
		int gx = transformToX(universe, x, y);
		int gy = transformToY(universe, x, y);
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
	public static int transformToRow(Universe universe, int x, int y) {
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
	public static int transformToX(int i, int j) {
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
	public static int transformToX(Universe universe, double x, double y) {
		Dimension size = universe.getSize();
		int w = size.width;
		int dw = w * 2 - 1;
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
	public static int transformToY(int i, int j) {
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
	public static int transformToY(Universe universe, double x, double y) {
		Dimension size = universe.getSize();
		int dh = size.height - 1;
		return (int) Math.round(y * dh);
	}
}
