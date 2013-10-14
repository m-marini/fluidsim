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
	private double value;

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getA(org.mmarini.fluid.model.TimeContext)
	 */
	public double getA(TimeContext timeContext) {
		return 0;
	}

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getB(org.mmarini.fluid.model.TimeContext)
	 */
	public double getB(TimeContext timeContext) {
		return 0;
	}

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getC(org.mmarini.fluid.model.TimeContext)
	 */
	public double getC(TimeContext timeContext) {
		return getValue();
	}

	/**
	 * @return the value
	 */
	private double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
