/*
 * FluidHandlerImpl.java
 *
 * $Id: FluidHandlerImpl.java,v 1.4 2007/08/18 08:29:54 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.awt.Dimension;

/**
 * The FluidHandlerImpl is the concrete implementation of a fluid handler.
 * <p>
 * It handles the interaction between the simulation client and the simulator
 * engine.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: FluidHandlerImpl.java,v 1.4 2007/08/18 08:29:54 marco Exp $
 * 
 */
public class FluidHandlerImpl implements FluidHandler {
    private Universe universe;

    private UniverseBuilder builder;

    private Simulator simulator;

    private UniverseDoubleFunction cellFunction;

    private UniverseDoubleFunction relationFunction;

    private UniverseDoubleFunction fluxFunction;

    /**
         * Returns the simulated universe.
         * 
         * @return the simulated universe
         */
    private Universe getUniverse() {
	return universe;
    }

    /**
         * Sets the simulated universe.
         * 
         * @param universe
         *                the universe to set
         */
    private void setUniverse(Universe universe) {
	this.universe = universe;
    }

    /**
         * @see FluidHandler#createNew()
         */
    public void createNew() {
	setUniverse(getBuilder().build());
    }

    /**
         * Returns the universe builder.
         * 
         * @return the builder
         */
    private UniverseBuilder getBuilder() {
	return builder;
    }

    /**
         * Sets the universe builder.
         * <p>
         * The builder is used to generate a new universe.
         * </p>
         * 
         * @param builder
         *                the builder to set
         */
    public void setBuilder(UniverseBuilder builder) {
	this.builder = builder;
    }

    /**
         * Returns the simulator.
         * 
         * @return the simulator
         */
    private Simulator getSimulator() {
	return simulator;
    }

    /**
         * Sets the simulator.
         * 
         * @param simulator
         *                the simulator to set
         */
    public void setSimulator(Simulator simulator) {
	this.simulator = simulator;
    }

    /**
         * @see FluidHandler#simulate()
         */
    public void simulate() {
	getSimulator().simulate(getUniverse());
    }

    /**
         * @see FluidHandler#singleStepSimulation()
         */
    public void singleStepSimulation() {
	getSimulator().singleStepSimulation(getUniverse());
    }

    /**
         * @see FluidHandler#startSimulation()
         */
    public void startSimulation() {
	getSimulator().startSimulation();
    }

    /**
         * @see FluidHandler#getSize()
         */
    public Dimension getSize() {
	return getUniverse().getSize();
    }

    /**
         * @see FluidHandler#getCellValue(int, int)
         */
    public double getCellValue(int i, int j) {
	return getCellFunction().getValue(getUniverse(), i, j);
    }

    /**
         * Returns the cell function.
         * 
         * @return the cellFunction
         */
    private UniverseDoubleFunction getCellFunction() {
	return cellFunction;
    }

    /**
         * Sets the cell function.
         * <p>
         * The cell function is used to calculate the cell graphic.
         * </p>
         * 
         * @param cellFunction
         *                the cellFunction to set
         */
    public void setCellFunction(UniverseDoubleFunction cellFunction) {
	this.cellFunction = cellFunction;
    }

    /**
         * @see FluidHandler#getRelationValue(int, int)
         */
    public double getRelationValue(int i, int j) {
	return getRelationFunction().getValue(getUniverse(), i, j);
    }

    /**
         * Returns the relation function.
         * 
         * @return the relationFunction
         */
    private UniverseDoubleFunction getRelationFunction() {
	return relationFunction;
    }

    /**
         * Sets the relation function.
         * <p>
         * The relation function is used to calculate the relation graphic.
         * </p>
         * 
         * @param relationFunction
         *                the relationFunction to set
         */
    public void setRelationFunction(UniverseDoubleFunction relationFunction) {
	this.relationFunction = relationFunction;
    }

    /**
         * @see FluidHandler#getSimulationRate()
         */
    public double getSimulationRate() {
	return 1000. * getSimulator().getLastCount()
		/ getSimulator().getLastElapsed();
    }

    /**
         * Returns the flux function.
         * 
         * @return the fluxFunction
         */
    public UniverseDoubleFunction getFluxFunction() {
	return fluxFunction;
    }

    /**
         * Sets the flux function.
         * 
         * @param fluxFunction
         *                the fluxFunction to set
         */
    public void setFluxFunction(UniverseDoubleFunction fluxFunction) {
	this.fluxFunction = fluxFunction;
    }

    /**
         * @see FluidHandler#getFluxValue(int, int)
         */
    public double getFluxValue(int i, int j) {
	return getFluxFunction().getValue(getUniverse(), i, j);
    }
}
