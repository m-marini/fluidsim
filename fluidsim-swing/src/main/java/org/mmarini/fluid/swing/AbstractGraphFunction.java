/*
 * AbstractGraphFunction.java
 *
 * $Id: AbstractGraphFunction.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

import java.awt.Dimension;

import org.mmarini.fluid.model.FluidHandler;

/**
 * The AbstractGraphFunction adapts the functionality of GraphPane from the data
 * model to be drawn.
 * <p>
 * The adapter must implements the following methods:
 * <dl>
 * <dt>getSize()</dt>
 * <dd>to gets the graphic size</dd>
 * <dt>getValue(int, int)</dt>
 * <dd>to get the value of an item</dd>
 * </dl>
 * </p>
 * 
 * @author US00852
 * @version $Id: AbstractGraphFunction.java,v 1.3 2007/08/18 08:29:55 marco Exp
 *          $
 */
public abstract class AbstractGraphFunction {
	private FluidHandler fluidHandler;

	/**
	 * Returns the fluid handler
	 * 
	 * @return the fluidHandler
	 */
	protected FluidHandler getFluidHandler() {
		return fluidHandler;
	}

	/**
	 * Returns the size of data.
	 * 
	 * @return the size
	 */
	public Dimension getSize() {
		return fluidHandler.getSize();
	}

	/**
	 * Returns the value of a item in i-row and j-column.
	 * <p>
	 * The returned values should be in 0 ... 1 range.
	 * </p>
	 * 
	 * @param i
	 *            index of row
	 * @param j
	 *            index of column
	 * @return the value
	 */
	public abstract double getValue(int i, int j);

	/**
	 * Sets the fluid handler.
	 * 
	 * @param fluidHandler
	 *            the fluidHandler to set
	 */
	public void setFluidHandler(FluidHandler fluidHandler) {
		this.fluidHandler = fluidHandler;
	}
}
