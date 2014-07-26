/*
 * ConstantFunction.java
 *
 * $Id: ConstantFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * @author marco
 * @version $Id: ConstantFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class ConstantFunction implements CoefficientFunction {
	private final double value;

	/**
	 * @param value
	 */
	public ConstantFunction() {
		this(0);
	}

	/**
	 * @param value
	 */
	public ConstantFunction(final double value) {
		this.value = value;
	}

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getA(org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getA(final TimeContext timeContext) {
		return 0;
	}

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getB(org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getB(final TimeContext timeContext) {
		return 0;
	}

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getC(org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getC(final TimeContext timeContext) {
		return getValue();
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
}
