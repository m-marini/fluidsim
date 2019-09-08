/*
 * Relation.java
 *
 * $Id: Relation.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * The Relation rappresents the relation value and function between the adjacent
 * cell.
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: Relation.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class Relation {
	private final DoubleBufferedDouble value = new DoubleBufferedDouble();

	private RelationFunction function;

	/**
	 * Returns the relation function.
	 * 
	 * @return the function
	 */
	public RelationFunction getFunction() {
		return function;
	}

	/**
	 * Returns the relation value.
	 * 
	 * @return the value
	 */
	public DoubleBufferedDouble getValue() {
		return value;
	}

	/**
	 * Sets the relation function.
	 * 
	 * @param function
	 *            the function to set
	 */
	public void setFunction(final RelationFunction function) {
		this.function = function;
	}
}
