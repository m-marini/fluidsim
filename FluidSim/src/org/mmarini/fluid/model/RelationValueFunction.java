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
public class RelationValueFunction implements UniverseDoubleFunction {
    private double scale;

    private double offset;

    /**
         * @see org.mmarini.fluid.model.UniverseDoubleFunction#getValue(org.mmarini.fluid.model.Universe,
         *      int, int)
         */
    public double getValue(Universe universe, int i, int j) {
	double v0 = getValue(universe, UniverseImpl.RIGHT, i, j)
		- getValue(universe, UniverseImpl.LEFT, i, j);
	double v1 = getValue(universe, UniverseImpl.UP_RIGHT, i, j)
		- getValue(universe, UniverseImpl.DOWN_LEFT, i, j);
	double v2 = getValue(universe, UniverseImpl.UP_LEFT, i, j)
		- getValue(universe, UniverseImpl.DOWN_RIGHT, i, j);
	return (v0 + v1 + v2 + getOffset()) * getScale();
    }

    /**
         * Calculates the value of relation in a given direction.
         * 
         * @param universe
         *                the universe
         * @param direction
         *                the direction
         * @param i
         *                the row index
         * @param j
         *                the column index
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
         * Returns the offset of the function.
         * 
         * @return the offset
         */
    public double getOffset() {
	return offset;
    }

    /**
         * Sets the offset of the function.
         * 
         * @param offset
         *                the offset to set
         */
    public void setOffset(double offset) {
	this.offset = offset;
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
         * Sets the scale of the function.
         * 
         * @param scale
         *                the scale to set
         */
    public void setScale(double scale) {
	this.scale = scale;
    }
}
