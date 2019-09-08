/*
 * DefaultRelationFunction.java
 *
 * $Id: DefaultRelationFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * @author marco
 * @version $Id: DefaultRelationFunction.java,v 1.3 2007/08/18 08:29:54 marco
 *          Exp $
 */
public class DefaultRelationFunction implements RelationFunction {
	private CoefficientFunction function;

	/**
	 * 
	 */
	public DefaultRelationFunction() {
	}

	/**
	 * @param function
	 */
	public DefaultRelationFunction(CoefficientFunction function) {
		this.function = function;
	}

	/**
	 * @see org.mmarini.fluid.model.RelationFunction#getA(int,
	 *      org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getA(int index, TimeContext timeContext) {
		double v = function.getA(timeContext);
		if (index == 0)
			return -v;
		return v;
	}

	/**
	 * @see org.mmarini.fluid.model.RelationFunction#getB(org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getB(TimeContext timeContext) {
		return function.getB(timeContext);
	}

	/**
	 * @see org.mmarini.fluid.model.RelationFunction#getC(org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getC(TimeContext timeContext) {
		return function.getC(timeContext);
	}

	/**
	 * @return the function
	 */
	public CoefficientFunction getFunction() {
		return function;
	}

	/**
	 * @param function
	 *            the function to set
	 */
	public void setFunction(CoefficientFunction function) {
		this.function = function;
	}

}