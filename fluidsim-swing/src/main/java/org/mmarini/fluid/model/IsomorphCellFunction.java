/*
 * IsomorphCellFunction.java
 *
 * $Id: IsomorphCellFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * The IsomorphCellFunction implements a isomorfic cell function.
 * <p>
 * The isomorfic function has the caracteristic that the coefficent A is
 * positive for the inbound relations (direction &lt; 3) and negative for the
 * outbound relations.<br>
 * The absolute value of the coefficent is the same for all the directions.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: IsomorphCellFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class IsomorphCellFunction implements CellFunction, FluidConstants {
	private CoefficientFunction function;

	/**
	 * 
	 */
	public IsomorphCellFunction() {
	}

	/**
	 * @param function
	 */
	public IsomorphCellFunction(final CoefficientFunction function) {
		this.function = function;
	}

	/**
	 * @see CellFunction#getA(int, TimeContext)
	 */
	@Override
	public double getA(final int direction, final TimeContext timeContext) {
		final double v = function.getA(timeContext);
		if (direction < RELATION_DIRECTIONS)
			return v;
		return -v;
	}

	/**
	 * @see CellFunction#getB(TimeContext)
	 */
	@Override
	public double getB(final TimeContext timeContext) {
		return function.getB(timeContext);
	}

	/**
	 * @see CellFunction#getC(TimeContext)
	 */
	@Override
	public double getC(final TimeContext timeContext) {
		return function.getC(timeContext);
	}

	/**
	 * @return the function
	 */
	public CoefficientFunction getFunction() {
		return function;
	}

	/**
	 * Sets the delegate coefficent function.
	 * 
	 * @param function
	 *            the function to set
	 */
	public void setFunction(final CoefficientFunction cellFunction) {
		this.function = cellFunction;
	}

}
