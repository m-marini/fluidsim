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
         * @see CellFunction#getA(int, TimeContext)
         */
    public double getA(int direction, TimeContext timeContext) {
	double v = getFunction().getA(timeContext);
	if (direction < RELATION_DIRECTIONS)
	    return v;
	return -v;
    }

    /**
         * @see CellFunction#getB(TimeContext)
         */
    public double getB(TimeContext timeContext) {
	return getFunction().getB(timeContext);
    }

    /**
         * @see CellFunction#getC(TimeContext)
         */
    public double getC(TimeContext timeContext) {
	return getFunction().getC(timeContext);
    }

    /**
         * Returns the delegate coefficent function.
         * 
         * @return the function
         */
    private CoefficientFunction getFunction() {
	return function;
    }

    /**
         * Sets the delegate coefficent function.
         * 
         * @param function
         *                the function to set
         */
    public void setFunction(CoefficientFunction cellFunction) {
	this.function = cellFunction;
    }

}
