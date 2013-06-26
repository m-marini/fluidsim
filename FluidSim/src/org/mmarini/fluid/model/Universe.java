/*
 * Universe.java
 *
 * $Id: Universe.java,v 1.5 2007/08/18 08:29:54 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.awt.Dimension;

/**
 * This is the interface of universes.
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: Universe.java,v 1.5 2007/08/18 08:29:54 marco Exp $
 */
public interface Universe {
    /**
         * Returns the size of universe.
         * 
         * @return the size of universe
         */
    public abstract Dimension getSize();

    /**
         * Returns the value of a cell.
         * 
         * @param i
         *                the row index
         * @param j
         *                the column index
         * @return the cell value
         */
    public abstract DoubleBufferedDouble getCell(int i, int j);

    /**
         * Performs a simulation step.
         * 
         * @param time
         *                the time interval of simulation in seconds
         */
    public abstract void simulate(double time);

    /**
         * Returns the value of a relation in a given direction.
         * 
         * @param direction
         *                the direction
         * @param i
         *                the row index
         * @param j
         *                the column index
         * @return the relation value
         */
    public abstract DoubleBufferedDouble getRelation(int direction, int i, int j);

    /**
         * Sets the cell function.
         * <p>
         * The cell function determines the way the next cell values are
         * calculated.
         * </p>
         * 
         * @param i
         *                the row index
         * @param j
         *                the column index
         * @param function
         *                the function
         */
    public abstract void setCellFunction(int i, int j, CellFunction function);

    /**
         * Sets the relation function.
         * <p>
         * The relation function determines the way the next relation values are
         * calculated.
         * </p>
         * 
         * @param i
         *                the row index
         * @param j
         *                the cell index
         * @param function
         *                the function
         */
    public abstract void setRelationFunction(int i, int j,
	    RelationFunction function);

    /**
         * Sets the relation function in a given direction.
         * 
         * @param direction
         *                the direction
         * @param i
         *                the row index
         * @param j
         *                the column index
         * @param function
         *                the function
         */
    public abstract void setRelationFunction(int direction, int i, int j,
	    RelationFunction function);
}
