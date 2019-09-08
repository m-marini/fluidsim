/*
 * CellUpdateContext.java
 *
 * $Id: CellUpdateContext.java,v 1.4 2007/08/18 08:29:55 marco Exp $
 *
 * 03/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The CellUpdateContext is the context used to update the cell values.
 * <p>
 * The function to update the relation is:
 * </p>
 * <p>
 * &gamma;(t+&Delta;t)=&Sigma; &rho;(i) * A(i,t) + &gamma;(t) * B(t) + C(t)
 * </p>
 * <p>
 * It uses a delegation function to calculate the coefficents.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: RelationUpdateContext.java,v 1.2.2.2 2007/08/14 20:31:30 marco
 *          Exp $
 */
public class CellUpdateContext extends AbstractUpdaterContext {
	private CellFunction function;

	/**
	 * Creates a cell update context
	 */
	protected CellUpdateContext() {
		setAdjacents(new DoubleBufferedDouble[6]);
	}

	/**
	 * Returns the A coefficent for a given direction.
	 *
	 * @param direction the direction
	 * @return the value
	 */
	public double getA(final int direction) {
		return function.getA(direction, getTimeContext());
	}

	/**
	 * Returns the B coefficent
	 *
	 * @return the value
	 */
	public double getB() {
		return function.getB(getTimeContext());
	}

	/**
	 * Returns the C coefficient
	 *
	 * @return the value
	 */
	public double getC() {
		return function.getC(getTimeContext());
	}

	/**
	 * Return the delegation function
	 *
	 * @return the function
	 */
	public CellFunction getFunction() {
		return function;
	}

	/**
	 * Sets the delegation function
	 *
	 * @param function the function to set
	 */
	public void setFunction(final CellFunction function) {
		this.function = function;
	}
}
