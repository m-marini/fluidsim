/*
 * Cell.java
 *
 * $Id: Cell.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * The Cell rappresents the cell value and function.
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: Cell.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 * 
 */
public class Cell {
	private DoubleBufferedDouble value;
	private CellFunction function;

	/**
	 * 
	 */
	public Cell() {
		value = new DoubleBufferedDouble();
	}

	/**
	 * Returns the cell function.
	 * 
	 * @return the function
	 */
	public CellFunction getFunction() {
		return function;
	}

	/**
	 * Returns the value of the cell
	 * 
	 * @return the value
	 */
	public DoubleBufferedDouble getValue() {
		return value;
	}

	/**
	 * Sets the cell function.
	 * <p>
	 * The function is used to calculate the next value of the cell depending on
	 * the adjacent relations.
	 * </p>
	 * 
	 * @param function
	 *            the function to set
	 */
	public void setFunction(CellFunction function) {
		this.function = function;
	}
}
