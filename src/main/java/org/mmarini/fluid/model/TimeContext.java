/*
 * TimeContext.java
 *
 * $Id: TimeContext.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 12/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The TimeContext is used to mantain the time parameters of the simulation.
 * <p>
 * It is used during the process simulation.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: TimeContext.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class TimeContext {
	private double time;

	private double deltaTime;

	/**
	 * Returns the current simulation interval.
	 *
	 * @return the current simulation interval in seconds
	 */
	public double getDeltaTime() {
		return deltaTime;
	}

	/**
	 * Returns the total simulation time in seconds.
	 *
	 * @return the total simulation time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Update the time parameters.
	 *
	 * @param deltaTime the interval in seconds
	 */
	public void update(final double deltaTime) {
		this.deltaTime = deltaTime;
		time += deltaTime;
	}
}
