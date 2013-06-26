/*
 * UniverseMock.java
 *
 * $Id: UniverseMock.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 *
 * 16/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.awt.Dimension;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: UniverseMock.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 * 
 */
public class UniverseMock implements Universe {
    private Dimension size;

    /*
         * (non-Javadoc)
         * 
         * @see org.mmarini.fluid.model.Universe#getCell(int, int)
         */
    public DoubleBufferedDouble getCell(int i, int j) {
	return null;
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.mmarini.fluid.model.Universe#getRelation(int, int, int)
         */
    public DoubleBufferedDouble getRelation(int direction, int i, int j) {
	return null;
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.mmarini.fluid.model.Universe#getSize()
         */
    public Dimension getSize() {
	return size;
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.mmarini.fluid.model.Universe#setCellFunction(int, int,
         *      org.mmarini.fluid.model.CellFunction)
         */
    public void setCellFunction(int i, int j, CellFunction function) {
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.mmarini.fluid.model.Universe#setRelationFunction(int, int,
         *      org.mmarini.fluid.model.RelationFunction)
         */
    public void setRelationFunction(int i, int j, RelationFunction function) {
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.mmarini.fluid.model.Universe#setRelationFunction(int, int,
         *      int, org.mmarini.fluid.model.RelationFunction)
         */
    public void setRelationFunction(int direction, int i, int j,
	    RelationFunction function) {
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.mmarini.fluid.model.Universe#simulate(double)
         */
    public void simulate(double time) {
    }

    /**
         * @param size
         *                the size to set
         */
    public void setSize(Dimension size) {
	this.size = size;
    }

}
