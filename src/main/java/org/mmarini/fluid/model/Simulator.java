/*
 * Simulator.java
 *
 * $Id: Simulator.java,v 1.4 2007/08/18 08:29:54 marco Exp $
 *
 * 08/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * The Simulator class manages the simulation timers.
 * <p>
 * Each real time simulation consists of at least of one simulation step and
 * iterates simulation steps until the minimum elasped is reached. The time of
 * universe is rated to the simulationRate property.
 * </p>
 * <p>
 * The single step simulation runs a single step with a fix interval.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: Simulator.java,v 1.4 2007/08/18 08:29:54 marco Exp $
 */
public class Simulator {
	private double singleStepInterval;

	private double simulationRate;

	private long minInterval;
	private long lastTime;
	private int lastCount;
	private long lastElapsed;

	/**
	 * 
	 */
	public Simulator() {
	}

	/**
	 * @param singleStepTime
	 * @param simulationRate
	 * @param minElapsed
	 */
	public Simulator(final double singleStepTime, final double simulationRate,
			final long minElapsed) {
		this.singleStepInterval = singleStepTime;
		this.simulationRate = simulationRate;
		this.minInterval = minElapsed;
	}

	/**
	 * Returns the last simulation step count.
	 * 
	 * @return the lastCount
	 */
	public int getLastCount() {
		return lastCount;
	}

	/**
	 * Returns the last simulation interval.
	 * 
	 * @return the lastElapsed in msec.
	 */
	public long getLastElapsed() {
		return lastElapsed;
	}

	/**
	 * @param minElapsed
	 *            the minElapsed to set
	 */
	public void setMinInterval(final long minElapsed) {
		this.minInterval = minElapsed;
	}

	/**
	 * Sets the simulation rate.
	 * <p>
	 * A 1 value means a real time simulation, a grater value means an
	 * accelerated simulation.
	 * </p>
	 * 
	 * @param simulationRate
	 *            the simulationRate to set
	 */
	public void setSimulationRate(final double simulationRate) {
		this.simulationRate = simulationRate;
	}

	/**
	 * Sets the single step time.
	 * <p>
	 * The value is used in the single step simulation.
	 * </p>
	 * 
	 * @param singleStepTime
	 *            the singleStepTime to set in seconds
	 */
	public void setSingleStepInterval(final double singleStepTime) {
		this.singleStepInterval = singleStepTime;
	}

	/**
	 * Performs a real time simulation cycle.
	 * 
	 * @param universe
	 *            the universe to simulate
	 */
	public void simulate(final Universe universe) {
		final long lastTime = this.lastTime;
		long lastStepTime = lastTime;
		int stepCount = 0;
		long elapsed = 0;
		for (;;) {
			final long time = System.currentTimeMillis();
			elapsed = time - lastTime;
			if (elapsed > minInterval && stepCount > 1)
				break;
			final long stepElaps = time - lastStepTime;
			final double dt = simulationRate * stepElaps / 1000;
			simulate(universe, dt);
			++stepCount;
			lastStepTime = time;
		}
		lastCount = stepCount;
		lastElapsed = elapsed;
		this.lastTime = lastStepTime;
	}

	/**
	 * Performs a simulation step.
	 * 
	 * @param universe
	 *            the universe to simulate
	 * 
	 */
	private void simulate(final Universe universe, final double time) {
		universe.simulate(time);
	}

	/**
	 * Performs a single step simulation
	 * 
	 * @param universe
	 *            the universe to simulate
	 */
	public void singleStepSimulation(final Universe universe) {
		simulate(universe, singleStepInterval);
	}

	/**
	 * Starts the timers of real time simulation.
	 */
	public void startSimulation() {
		lastTime = System.currentTimeMillis();
	}
}
