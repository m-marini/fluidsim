/*
 * CellShape.java
 *
 * $Id: CellShape.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 * 07/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

import java.awt.Graphics;

/**
 * The CellShape draws the structure of a cell.
 * <p>
 * Depending on the graphical size of the cell it draws a rectangular shape for
 * small cells and an exagonal shape for the other ones.
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

    private int[][] template = new int[2][EDGES_COUNT];

    private int[][] points = new int[2][EDGES_COUNT];

    /**
         * Creates the Shape.
         * <p>
         * Calculates the positions of point of the exagonal shape.
         * </p>
         */
    public CellShape() {
	int[] templx = getTemplate()[0];
	int[] temply = getTemplate()[1];
	for (int i = 0; i < EDGES_COUNT; ++i) {
	    double a = Math.PI * 2 * i / EDGES_COUNT;
	    templx[i] = (int) Math.round(Math.sin(a) * RADIUS + XOFFSET);
	    temply[i] = (int) Math.round(Math.cos(a) * RADIUS + YOFFSET);
	}
    }

    /**
         * Draws the shape
         * 
         * @param gr
         *                the graphics context
         * @param x
         *                the x position (left)
         * @param y
         *                the y position (bottom)
         * @param w
         *                the width of the cell
         * @param h
         *                the heigh of the cell
         */
    public void draw(Graphics gr, int x, int y, int w, int h) {
	if (w <= MIN_SIZE || h <= MIN_SIZE) {
	    gr.fillRect(x, y, w, h);
	} else {
	    int[] ptsx = getPoints()[0];
	    int[] ptsy = getPoints()[1];
	    int[] templx = getTemplate()[0];
	    int[] temply = getTemplate()[1];
	    int w1 = (w - 1);
	    int h1 = (h - 1);
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
         *                the numerator
         * @param d
         *                the denominator
         * @return the value
         */
    private int roundDiv(int n, int d) {
	return (n + n + d) / (2 * d);
    }

    /**
         * Returns the array used to store the drawing coordinates.
         * 
         * @return the points
         */
    private int[][] getPoints() {
	return points;
    }

    /**
         * Returns the array of the coordinates of the template shape.
         * 
         * @return the template
         */
    private int[][] getTemplate() {
	return template;
    }

}
