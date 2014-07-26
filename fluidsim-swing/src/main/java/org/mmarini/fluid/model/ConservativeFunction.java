/*
 * ConservativeFunction.java
 *
 * $Id: ConservativeFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The ConservativeFunction generates the default behaviour of cell values.
 * <p>
 * The equation is:
 * </p>
 * <p>
 * &phi;(t+&Delta;t) = &phi;(t) + &omega;(t) &Delta;t
 * </p>
 * <p>
 * <ul>
 * <li>a = &Delta;t</li>
 * <li>b = 1</li>
 * <li>c = 0</li>
 * </ul>
 * </p>
 * 
 * @author US00852
 * @version $Id: ConservativeFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 * 
 */
public class ConservativeFunction implements CoefficientFunction {

	/**
         * 
         */
	@Override
	public double getA(final TimeContext timeContext) {
		return timeContext.getDeltaTime();
	}

	/**
         * 
         */
	@Override
	public double getB(final TimeContext timeContext) {
		return 1;
	}

	/**
         * 
         */
	@Override
	public double getC(final TimeContext timeContext) {
		return 0;
	}
}
