/*
 * CellValueFunction.java
 *
 * $Id: CellValueFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * The CellValueFunction calculates the value of a cell.
 * <p>
 * It is used to draw the graphic of the cells.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: CellValueFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 */
public class CellValueFunction implements UniverseFunction {
	private double scale;

	private double offset;

	/**
	 *
	 */
	public CellValueFunction() {
	}

	/**
	 * @param scale
	 * @param offset
	 */
	public CellValueFunction(final double scale, final double offset) {
		this.scale = scale;
		this.offset = offset;
	}

	/**
	 * Returns the offset of function.
	 *
	 * @return the offset
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * Returns the scale of function.
	 *
	 * @return the scale
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * @see org.mmarini.fluid.model.UniverseFunction#getValue(org.mmarini.fluid.model.Universe,
	 *      int, int)
	 */
	@Override
	public double getValue(final Universe universe, final int i, final int j) {
		return (universe.getCell(i, j).getValue() - getOffset()) * getScale();
	}

	/**
	 * Sets the offset of function.
	 *
	 * @param offset the offset to set
	 */
	public void setOffset(final double offset) {
		this.offset = offset;
	}

	/**
	 * Sets the scale of function.
	 *
	 * @param scale the scale to set
	 */
	public void setScale(final double scale) {
		this.scale = scale;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("CellValueFunction [scale=").append(scale).append(", offset=").append(offset).append("]");
		return builder.toString();
	}

}
