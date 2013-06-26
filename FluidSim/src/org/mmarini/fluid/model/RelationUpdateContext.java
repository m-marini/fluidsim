/*
 * RelationUpdateContext.java
 *
 * $Id: RelationUpdateContext.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 03/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The RelationUpdateContext is the context used to update the relation values.
 * <p>
 * The function to update the relation is:
 * </p>
 * <p>
 * &rho;(t+&Delta;t)=&Sigma; &gamma;(i) * A(i,t) + &rho;(t) * B(t) + C(t)
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: RelationUpdateContext.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class RelationUpdateContext extends AbstractUpdaterContext {
    private RelationFunction function;

    /**
         * Creates a context.
         */
    protected RelationUpdateContext() {
	setAdjacents(new DoubleBufferedDouble[2]);
    }

    /**
         * Returns the A constant reltaive to the cell index.
         * <p>
         * The A constant is the moltiplier in the relation function relative to
         * the cell value. The index determines the cell context:
         * <ul>
         * <li>0 for the subject cell,</li>
         * <li>1 for the adjacent cell)</li>
         * </ul>
         * </p>
         * 
         * @param i
         *                the index index
         * @return the constant value
         */
    public double getA(int i) {
	return getFunction().getA(i, getTimeContext());
    }

    /**
         * Returns the C constant.
         * <p>
         * The C constant is the constant term in the relation function.
         * </p>
         * 
         * @return the constant value
         */
    public double getC() {
	return getFunction().getC(getTimeContext());
    }

    /**
         * Returns the C constant.
         * <p>
         * The B constant is the constant term in the relation function relative
         * to the previous relation value.
         * </p>
         * 
         * @return the constant value
         */
    public double getB() {
	return getFunction().getB(getTimeContext());
    }

    /**
         * Returns the delegated function.
         * 
         * @return the function
         */
    public RelationFunction getFunction() {
	return function;
    }

    /**
         * Sets the delegate function.
         * 
         * @param function
         *                the function to set
         */
    public void setFunction(RelationFunction function) {
	this.function = function;
    }
}
