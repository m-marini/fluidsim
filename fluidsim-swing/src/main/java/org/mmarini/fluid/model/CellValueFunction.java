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
public class CellValueFunction implements UniverseDoubleFunction {
	private double scale;

	private double offset;

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
	 * @see org.mmarini.fluid.model.UniverseDoubleFunction#getValue(org.mmarini.fluid.model.Universe,
	 *      int, int)
	 */
	@Override
	public double getValue(Universe universe, int i, int j) {
		return (universe.getCell(i, j).getValue() - getOffset()) * getScale();
	}

	/**
	 * Sets the offset of function.
	 * 
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	/**
	 * Sets the scale of function.
	 * 
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}

}
