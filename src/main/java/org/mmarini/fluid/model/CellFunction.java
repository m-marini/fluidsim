/*
 * CellFunction.java
 *
 * $Id: CellFunction.java,v 1.4 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * This is the interface of the cell functions.
 * <p>
 * The cell functions are the functions used to calculate the cell in the
 * simulation.
 * </p>
 * <p>
 * The general function is:
 * </p>
 * <p>
 * &gamma;(t+&Delta;t)=&Sigma; &rho;(i) * A(i,t,&Delta;t) + &gamma;(t) *
 * B(t,&Delta;t) + C(t,&Delta;t)
 * </p>
 * <p>
 * The interface declares the three coefficents A, B, C.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: CellFunction.java,v 1.4 2007/08/18 08:29:54 marco Exp $
 *
 */
public interface CellFunction {
	/**
	 * Returns the A coefficent.
	 *
	 * @param direction   the direction
	 * @param timeContext the time context
	 * @return the value
	 */
	public abstract double getA(int direction, TimeContext timeContext);

	/**
	 * Returns the B coefficent.
	 *
	 * @param timeContext the time context
	 * @return the value
	 */
	public abstract double getB(TimeContext timeContext);

	/**
	 * Returns the C coefficent.
	 *
	 * @param timeContext the time context
	 * @return the value
	 */
	public abstract double getC(TimeContext timeContext);
}
