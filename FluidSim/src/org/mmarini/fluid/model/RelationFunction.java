/*
 * RelationFunction.java
 *
 * $Id: RelationFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * This is the interface of the relation functions.
 * <p>
 * The relation functions are the functions used to calculate the cell relations
 * in the simulation.
 * </p>
 * <p>
 * The general function is:
 * </p>
 * <p>
 * &rho;(t+&Delta;t)=&Sigma; &gamma;(i) * A(i,t,&Delta;t) + &rho;(t) *
 * B(t,&Delta;t) + C(t,&Delta;t)
 * </p>
 * <p>
 * The interface declares the three coefficents A, B, C.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: RelationFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public interface RelationFunction {
    /**
         * Returns the A coefficent.
         * 
         * @param index
         *                the index
         *                <ul>
         *                <li>0 for subject cell</li>
         *                <li>1 for adjacent cell</li>
         *                </ul>
         * @param timeContext
         *                the time context
         * @return the value
         */
    public abstract double getA(int index, TimeContext timeContext);

    /**
         * Returns the B coefficent.
         * 
         * @param timeContext
         *                the time context
         * @return the value
         */
    public abstract double getB(TimeContext timeContext);

    /**
         * Returns the C coefficent.
         * 
         * @param timeContext
         *                the time context
         * @return the value
         */
    public abstract double getC(TimeContext timeContext);

}
