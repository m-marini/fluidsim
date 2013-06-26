/*
 * UniverseDoubleFunction.java
 *
 * $Id: UniverseDoubleFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * This is the interface of the functions that calculate the value of the
 * universe to be drawn.
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: UniverseDoubleFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public interface UniverseDoubleFunction {
    /**
         * Returns the value to be drawn.
         * 
         * @param universe
         *                the universe
         * @param i
         *                the row index
         * @param j
         *                the column index
         * @return the value in 0 ... 1 range
         */
    public double getValue(Universe universe, int i, int j);
}
