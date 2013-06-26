package org.mmarini.fluid.model;

/**
 * The DoubleBufferedDouble has a pair of double value.
 * <p>
 * It behaves as a double buffer for double value. Both the values can be read
 * but only the next value can be set. The swap method set the value with the
 * next value.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: DoubleBufferedDouble.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 * 
 */
public class DoubleBufferedDouble {
    private double value;

    private double nextValue;

    /**
         * Copy the next value to the current value.
         */
    public void swap() {
	setValue(getNextValue());
    }

    /**
         * Returns the next value.
         * 
         * @return the nextValue
         */
    public double getNextValue() {
	return nextValue;
    }

    /**
         * Sets the next value.
         * 
         * @param nextValue
         *                the nextValue to set
         */
    public void setNextValue(double next) {
	this.nextValue = next;
    }

    /**
         * Sets the current value.
         * 
         * @param value
         *                the value to set
         */
    private void setValue(double value) {
	this.value = value;
    }

    /**
         * Returns the current value.
         * 
         * @return the value
         */
    public double getValue() {
	return value;
    }

    /**
         * @see java.lang.Object#toString()
         */
    @Override
    public String toString() {
	StringBuffer bfr = new StringBuffer();
	bfr.append("(value=");
	bfr.append(getValue());
	bfr.append(",nextValue=");
	bfr.append(getNextValue());
	bfr.append(")");
	return bfr.toString();
    }
}
