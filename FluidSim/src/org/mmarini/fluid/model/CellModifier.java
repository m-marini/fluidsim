/*
 * CellModifier.java
 *
 * $Id: CellModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 12/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * This is the interface of cell modifiers.
 * <p>
 * The cell modifiers change the attribute of a cell. They are used to create
 * the inital status of the universe.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: CellModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 * 
 */
public interface CellModifier {
    /**
         * Modifies a cell of a universe.
         * 
         * @param universe
         *                the universe
         * @param i
         *                the row index
         * @param j
         *                the column index
         */
    public abstract void modify(Universe universe, int i, int j);
}
