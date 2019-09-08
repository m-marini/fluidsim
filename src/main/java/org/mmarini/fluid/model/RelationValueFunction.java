/*
 * RelationValueFunction.java
 *
 * $Id: RelationValueFunction.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * The RelationValueFunction calculates the value of the speed in the relations.
 * <p>
 * The speed is modulo of the relation vector outbound the cell.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: RelationValueFunction.java,v 1.1.2.1 2007/08/16 08:07:29 marco
 *          Exp $
 */
public class RelationValueFunction implements UniverseFunction {
	private double scale;

	private double offset;

	/**
	 *
	 */
	public RelationValueFunction() {
	}

	/**
	 * @param scale
	 * @param offset
	 */
	public RelationValueFunction(final double scale, final double offset) {
		this.scale = scale;
		this.offset = offset;
	}

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
	 * @see org.mmarini.fluid.model.UniverseFunction#getValue(org.mmarini.fluid.model.Universe,
	 *      int, int)
	 */
	@Override
	public double getValue(final Universe universe, final int i, final int j) {
		final double v0 = getValue(universe, FluidConstants.RIGHT, i, j)
				- getValue(universe, FluidConstants.LEFT, i, j);
		final double v1 = getValue(universe, FluidConstants.UP_RIGHT, i, j)
				- getValue(universe, FluidConstants.DOWN_LEFT, i, j);
		final double v2 = getValue(universe, FluidConstants.UP_LEFT, i, j)
				- getValue(universe, FluidConstants.DOWN_RIGHT, i, j);
		return (v0 + v1 + v2 + getOffset()) * getScale();
	}

	/**
	 * Calculates the value of relation in a given direction.
	 *
	 * @param universe  the universe
	 * @param direction the direction
	 * @param i         the row index
	 * @param j         the column index
	 * @return the value
	 */
	private double getValue(final Universe universe, final int direction, final int i, final int j) {
		final DoubleBufferedDouble rel = universe.getRelation(direction, i, j);
		return rel != null ? rel.getValue() : 0;
	}

	/**
	 * Sets the offset of the function.
	 *
	 * @param offset the offset to set
	 */
	public void setOffset(final double offset) {
		this.offset = offset;
	}

	/**
	 * Sets the scale of the function.
	 *
	 * @param scale the scale to set
	 */
	public void setScale(final double scale) {
		this.scale = scale;
	}
}
