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
         * @see CoefficientFunction#getA(TimeContext)
         */
    public double getA(TimeContext timeContext) {
	return 0;
    }

    /**
         * @see CoefficientFunction#getB(TimeContext)
         */
    public double getB(TimeContext timeContext) {
	return 0;
    }

    /**
         * @see CoefficientFunction#getC(TimeContext)
         */
    public double getC(TimeContext timeContext) {
	return getValue() * Math.sin(PI2 * timeContext.getTime() / getPeriod())
		* 0.5 + 0.5;
    }

    /**
         * Returns the value of function.
         * 
         * @return the value
         */
    private double getValue() {
	return value;
    }

    /**
         * Sets the value of function.
         * 
         * @param value
         *                the value to set
         */
    public void setValue(double value) {
	this.value = value;
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
         * Sets the period of function.
         * 
         * @param period
         *                the period to set in seconds
         */
    public void setPeriod(double period) {
	this.period = period;
    }
}
