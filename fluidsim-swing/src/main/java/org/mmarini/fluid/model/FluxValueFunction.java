/*
 * FluxValueFunction.java
 *
 * $Id: FluxValueFunction.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * The FluxValueFunction calculates the value of the speed in the relations.
 * <p>
 * The speed is modulo of the relation vector outbound the cell.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: FluxValueFunction.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 */
public class FluxValueFunction implements UniverseDoubleFunction {
	private static final double SIN60 = Math.sin(Math.PI / 3);

	private static final double COS60 = Math.cos(Math.PI / 3);

	private double scale;

	private double offset;

	/**
	 * Returns the offset of the function.
	 * 
	 * @return the offset
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * Returns the scale of the function.
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
	public double getValue(Universe universe, int i, int j) {
		double v0 = getValue(universe, FluidConstants.RIGHT, i, j)
				+ getValue(universe, FluidConstants.LEFT, i, j);
		double v1 = getValue(universe, FluidConstants.UP_RIGHT, i, j)
				+ getValue(universe, FluidConstants.DOWN_LEFT, i, j);
		double v2 = getValue(universe, FluidConstants.UP_LEFT, i, j)
				+ getValue(universe, FluidConstants.DOWN_RIGHT, i, j);
		double vx = v0 + SIN60 * (v1 - v2);
		double vy = COS60 * (v1 + v2);
		return (Math.sqrt(vx * vx + vy * vy) + getOffset()) * getScale();
	}

	/**
	 * Calculates the value of relation in a given direction.
	 * 
	 * @param universe
	 *            the universe
	 * @param direction
	 *            the direction
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return the value
	 */
	private double getValue(Universe universe, int direction, int i, int j) {
		DoubleBufferedDouble rel = universe.getRelation(direction, i, j);
		if (rel != null) {
			return rel.getValue();
		}
		return 0;
	}

	/**
	 * Sets the offset of the function.
	 * 
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	/**
	 * Sets the scale of the function.
	 * 
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}
}
