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
	 * 
	 */
	public FluidHandlerImpl() {
	}

	/**
	 * @see FluidHandler#createNew()
	 */
	public void createNew() {
		universe = builder.build();
	}

	/**
	 * @see FluidHandler#getCellValue(int, int)
	 */
	public double getCellValue(int i, int j) {
		return cellFunction.getValue(universe, i, j);
	}

	/**
	 * @see FluidHandler#getFluxValue(int, int)
	 */
	public double getFluxValue(int i, int j) {
		return fluxFunction.getValue(universe, i, j);
	}

	/**
	 * @see FluidHandler#getRelationValue(int, int)
	 */
	public double getRelationValue(int i, int j) {
		return relationFunction.getValue(universe, i, j);
	}

	/**
	 * @see FluidHandler#getSimulationRate()
	 */
	public double getSimulationRate() {
		return 1000. * simulator.getLastCount() / simulator.getLastElapsed();
	}

	/**
	 * @see FluidHandler#getSize()
	 */
	public Dimension getSize() {
		return universe.getSize();
	}

	/**
	 * Sets the universe builder.
	 * <p>
	 * The builder is used to generate a new universe.
	 * </p>
	 * 
	 * @param builder
	 *            the builder to set
	 */
	public void setBuilder(UniverseBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Sets the cell function.
	 * <p>
	 * The cell function is used to calculate the cell graphic.
	 * </p>
	 * 
	 * @param cellFunction
	 *            the cellFunction to set
	 */
	public void setCellFunction(UniverseDoubleFunction cellFunction) {
		this.cellFunction = cellFunction;
	}

	/**
	 * Sets the flux function.
	 * 
	 * @param fluxFunction
	 *            the fluxFunction to set
	 */
	public void setFluxFunction(UniverseDoubleFunction fluxFunction) {
		this.fluxFunction = fluxFunction;
	}

	/**
	 * Sets the relation function.
	 * <p>
	 * The relation function is used to calculate the relation graphic.
	 * </p>
	 * 
	 * @param relationFunction
	 *            the relationFunction to set
	 */
	public void setRelationFunction(UniverseDoubleFunction relationFunction) {
		this.relationFunction = relationFunction;
	}

	/**
	 * Sets the simulator.
	 * 
	 * @param simulator
	 *            the simulator to set
	 */
	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	/**
	 * @see FluidHandler#simulate()
	 */
	public void simulate() {
		simulator.simulate(universe);
	}

	/**
	 * @see FluidHandler#singleStepSimulation()
	 */
	public void singleStepSimulation() {
		simulator.singleStepSimulation(universe);
	}

	/**
	 * @see FluidHandler#startSimulation()
	 */
	public void startSimulation() {
		simulator.startSimulation();
	}
}
