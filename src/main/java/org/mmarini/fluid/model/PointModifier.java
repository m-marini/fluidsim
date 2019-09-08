/*
 * PointModifier.java
 *
 * $Id: PointModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 03/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The PointModifier modifies the universe applying the cell modifier to the
 * cell in the point defined by the properties.
 * <p>
 * The point is determinde by (x,y). The coordinate is in 0 ... 1 range.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: PointModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class PointModifier extends AbstractUniverseModifier {
	private double x;
	private double y;

	/**
	 *
	 */
	public PointModifier() {
	}

	/**
	 * @param cellModifier
	 * @param x
	 * @param y
	 */
	public PointModifier(final CellModifier cellModifier, final double x, final double y) {
		super(cellModifier);
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x value
	 *
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y value.
	 *
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @see UniverseModifier#modify(Universe)
	 */
	@Override
	public void modify(final Universe universe) {
		final double x02 = getX();
		final double y02 = getY();
		final int x0 = Utils.transformToColumn(universe, x02, y02);
		final int y0 = Utils.transformToRow(universe, x02, y02);
		getCellModifier().modify(universe, y0, x0);
	}

	/**
	 * Sets the x value
	 *
	 * @param x the x to set
	 */
	public void setX(final double x) {
		this.x = x;
	}

	/**
	 * Sets the y value.
	 *
	 * @param y the y to set
	 */
	public void setY(final double y) {
		this.y = y;
	}
}
