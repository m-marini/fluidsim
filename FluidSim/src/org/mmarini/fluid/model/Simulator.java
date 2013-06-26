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
    private double singleStepTime;

    private double simulationRate;

    private long minElapsed;

    private long lastTime;

    private int lastCount;

    private long lastElapsed;

    /**
         * Starts the timers of real time simulation.
         */
    public void startSimulation() {
	setLastTime(System.currentTimeMillis());
    }

    /**
         * Performs a single step simulation
         * 
         * @param universe
         *                the universe to simulate
         */
    public void singleStepSimulation(Universe universe) {
	simulate(universe, getSingleStepTime());
    }

    /**
         * Performs a real time simulation cycle.
         * 
         * @param universe
         *                the universe to simulate
         */
    public void simulate(Universe universe) {
	long lastTime = getLastTime();
	long lastStepTime = lastTime;
	int stepCount = 0;
	long elapsed = 0;
	for (;;) {
	    long time = System.currentTimeMillis();
	    elapsed = time - lastTime;
	    if (elapsed > getMinElapsed() && stepCount > 1)
		break;
	    long stepElaps = time - lastStepTime;
	    double dt = getSimulationRate() * stepElaps / 1000;
	    simulate(universe, dt);
	    ++stepCount;
	    lastStepTime = time;
	}
	setLastCount(stepCount);
	setLastElapsed(elapsed);
	setLastTime(lastStepTime);
    }

    /**
         * Performs a simulation step.
         * 
         * @param universe
         *                the universe to simulate
         * 
         */
    private void simulate(Universe universe, double time) {
	universe.simulate(time);
    }

    /**
         * Returns the single step time.
         * 
         * @return the singleStepTime in seconds
         */
    private double getSingleStepTime() {
	return singleStepTime;
    }

    /**
         * Sets the single step time.
         * <p>
         * The value is used in the single step simulation.
         * </p>
         * 
         * @param singleStepTime
         *                the singleStepTime to set in seconds
         */
    public void setSingleStepTime(double singleStepTime) {
	this.singleStepTime = singleStepTime;
    }

    /**
         * Returns the last simulation time
         * 
         * @return the lastTime in msec
         */
    private long getLastTime() {
	return lastTime;
    }

    /**
         * Sets the last simulation time
         * 
         * @param lastTime
         *                the lastTime to set in msec
         */
    private void setLastTime(long lastTime) {
	this.lastTime = lastTime;
    }

    /**
         * Returns the minimum simulation interval
         * 
         * @return the minElapsed in msec.
         */
    private long getMinElapsed() {
	return minElapsed;
    }

    /**
         * @param minElapsed
         *                the minElapsed to set
         */
    public void setMinElapsed(long minElapsed) {
	this.minElapsed = minElapsed;
    }

    /**
         * Returns the simulation rate.
         * 
         * @return the simulationRate
         */
    private double getSimulationRate() {
	return simulationRate;
    }

    /**
         * Sets the simulation rate.
         * <p>
         * A 1 value means a real time simulation, a grater value means an
         * accelerated simulation.
         * </p>
         * 
         * @param simulationRate
         *                the simulationRate to set
         */
    public void setSimulationRate(double simulationRate) {
	this.simulationRate = simulationRate;
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
         * Sets the last simulation count.
         * 
         * @param lastCount
         *                the lastCount to set
         */
    private void setLastCount(int lastCount) {
	this.lastCount = lastCount;
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
         * Sets the last simulation interval
         * 
         * @param lastElapsed
         *                the lastElapsed to set in msec.
         */
    private void setLastElapsed(long lastElapsed) {
	this.lastElapsed = lastElapsed;
    }
}
