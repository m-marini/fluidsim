/*
 * OscillatorFunction.java
 *
 * $Id: OscillatorFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The OscillatorFunction generates a sin value depending on time.
 * <p>
 * <ul>
 * <li>A=0</li>
 * <li>B=0</li>
 * <li>C = k * sin(&omega;t) / 2 + 1 / 2</li>
 * </ul>
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: OscillatorFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class OscillatorFunction implements CoefficientFunction {
	private static final double PI2 = Math.PI * 2;

	private double period;

	private double value;

	/**
	 *
	 */
	public OscillatorFunction() {
	}

	/**
	 * @param value
	 * @param period
	 */
	public OscillatorFunction(final double value, final double period) {
		this.value = value;
		this.period = period;
	}

	/**
	 * @see CoefficientFunction#getA(TimeContext)
	 */
	@Override
	public double getA(final TimeContext timeContext) {
		return 0;
	}

	/**
	 * @see CoefficientFunction#getB(TimeContext)
	 */
	@Override
	public double getB(final TimeContext timeContext) {
		return 0;
	}

	/**
	 * @see CoefficientFunction#getC(TimeContext)
	 */
	@Override
	public double getC(final TimeContext timeContext) {
		return value * Math.sin(PI2 * timeContext.getTime() / period) * 0.5 + 0.5;
	}

	/**
	 * Returns the period of function
	 *
	 * @return the period in seconds
	 */
	public double getPeriod() {
		return period;
	}

	/**
	 * Returns the value of function.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the period of function.
	 *
	 * @param period the period to set in seconds
	 */
	public void setPeriod(final double period) {
		this.period = period;
	}

	/**
	 * Sets the value of function.
	 *
	 * @param value the value to set
	 */
	public void setValue(final double value) {
		this.value = value;
	}
}
