/*
 * RectangleModifier.java
 *
 * $Id: RectangleModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 03/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The RectangleModifier modifies the universe applying the cell modifier to the
 * cells in the rectangle defined by the properties.
 * <p>
 * The rectangle is delimited by two opposite corner (x0,y0) and (x1,y1). The
 * coordinate is in 0 ... 1 range.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: RectangleModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class RectangleModifier extends AbstractUniverseModifier {
	private double x0;
	private double y0;
	private double x1;
	private double y1;

	/**
	 *
	 */
	public RectangleModifier() {
	}

	/**
	 * @param cellModifier
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	public RectangleModifier(final CellModifier cellModifier, final double x0, final double y0, final double x1,
			final double y1) {
		super(cellModifier);
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}

	/**
	 * @return the x0
	 */
	public double getX0() {
		return x0;
	}

	/**
	 * @return the x1
	 */
	public double getX1() {
		return x1;
	}

	/**
	 * @return the y0
	 */
	public double getY0() {
		return y0;
	}

	/**
	 * @return the y1
	 */
	public double getY1() {
		return y1;
	}

	/**
	 * @see UniverseModifier#modify(Universe)
	 */
	@Override
	public void modify(final Universe universe) {
		final double x02 = x0;
		final double y02 = y0;
		final int x0 = Utils.transformToColumn(universe, x02, y02);
		final int y0 = Utils.transformToRow(universe, x02, y02);
		final double x12 = x1;
		final double y12 = y1;
		final int x1 = Utils.transformToColumn(universe, x12, y12);
		final int y1 = Utils.transformToRow(universe, x12, y12);
		final CellModifier modifier = getCellModifier();
		for (int i = y0; i <= y1; ++i) {
			for (int j = x0; j <= x1; ++j) {
				modifier.modify(universe, i, j);
			}
		}
	}

	/**
	 * Sets the x0 value.
	 *
	 * @param x0 the x0 to set
	 */
	public void setX0(final double x) {
		this.x0 = x;
	}

	/**
	 * Sets the x1 value
	 *
	 * @param x1 the x1 to set
	 */
	public void setX1(final double size) {
		this.x1 = size;
	}

	/**
	 * Sets the y0 value.
	 *
	 * @param y0 the y0 to set
	 */
	public void setY0(final double y) {
		this.y0 = y;
	}

	/**
	 * Sets the y1 value.
	 *
	 * @param y1 the y1 to set
	 */
	public void setY1(final double height) {
		this.y1 = height;
	}
}
