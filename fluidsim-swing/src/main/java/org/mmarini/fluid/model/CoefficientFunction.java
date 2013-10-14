/*
 * CoefficientFunction.java
 *
 * $Id: CoefficientFunction.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * This is the interface of the coefficient functions.
 * <p>
 * The coefficient functions rappresents different types of material.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: CoefficientFunction.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 * 
 */
public interface CoefficientFunction {
	/**
	 * Returns the A coefficient.
	 * 
	 * @param timeContext
	 *            the time context
	 * @return the value
	 */
	public abstract double getA(TimeContext timeContext);

	/**
	 * Returns the B coefficient.
	 * 
	 * @param timeContext
	 *            the time context
	 * @return the value
	 */
	public abstract double getB(TimeContext timeContext);

	/**
	 * Returns the C coefficient.
	 * 
	 * @param timeContext
	 *            the time context
	 * @return the value
	 */
	public abstract double getC(TimeContext timeContext);
}
