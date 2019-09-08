/*
 * FluidConstants.java
 *
 * $Id: FluidConstants.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The interface declares the constants of simulation.
 *
 * @author marco.marini@mmarini.org
 * @version $Id: FluidConstants.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 */
public interface FluidConstants {
	/**
	 * Right direction
	 */
	public static final int RIGHT = 0;

	/**
	 * Up right direction
	 */
	public static final int UP_RIGHT = 1;

	/**
	 * Up left direction
	 */
	public static final int UP_LEFT = 2;

	/**
	 * Left direction
	 */
	public static final int LEFT = 3;

	/**
	 * Down left direction
	 */
	public static final int DOWN_LEFT = 4;

	/**
	 * Down left direction
	 */
	public static final int DOWN_RIGHT = 5;

	/**
	 * Number of base relation directions
	 */
	public static final int RELATION_DIRECTIONS = 3;

}