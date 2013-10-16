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
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.mmarini.fluid.xml.FluidParser;
import org.xml.sax.SAXException;

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
	private static final double FLUX_OFFSET = 0.;
	private static final double FLUX_SCALE = 600e-3;
	private static final double SPEED_SCALE = 2000.;
	private static final double SPEED_OFFSET = 0.;
	private static final double CELL_OFFSET = 0.45;
	private static final double CELL_SCALE = 10.;
	private static final long MIN_INTERVAL = 80;
	private static final double SIMULATION_RATE = 1.0;
	private static final double SINGLE_STEP_INTERVAL = 0.1;

	private Universe universe;
	private UniverseBuilderImpl builder;
	private Simulator simulator;
	private UniverseFunction cellFunction;
	private UniverseFunction relationFunction;
	private UniverseFunction fluxFunction;

	/**
	 * 
	 */
	public FluidHandlerImpl() {
		simulator = new Simulator(SINGLE_STEP_INTERVAL, SIMULATION_RATE,
				MIN_INTERVAL);
		cellFunction = new CellValueFunction(CELL_SCALE, CELL_OFFSET);
		relationFunction = new RelationValueFunction(SPEED_SCALE, SPEED_OFFSET);
		fluxFunction = new FluxValueFunction(FLUX_SCALE, FLUX_OFFSET);
		builder = new UniverseBuilderImpl();
	}

	/**
	 * @see FluidHandler#createNew()
	 */
	@Override
	public void createNew() {
		universe = builder.build();
	}

	/**
	 * @see FluidHandler#getCellValue(int, int)
	 */
	@Override
	public double getCellValue(int i, int j) {
		return cellFunction.getValue(universe, i, j);
	}

	/**
	 * @see FluidHandler#getFluxValue(int, int)
	 */
	@Override
	public double getFluxValue(int i, int j) {
		return fluxFunction.getValue(universe, i, j);
	}

	/**
	 * @see FluidHandler#getRelationValue(int, int)
	 */
	@Override
	public double getRelationValue(int i, int j) {
		return relationFunction.getValue(universe, i, j);
	}

	/**
	 * @see FluidHandler#getSimulationRate()
	 */
	@Override
	public double getSimulationRate() {
		return 1000. * simulator.getLastCount() / simulator.getLastElapsed();
	}

	/**
	 * @see FluidHandler#getSize()
	 */
	@Override
	public Dimension getSize() {
		return universe.getSize();
	}

	/**
	 * 
	 * @param url
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Override
	public void loadUniverseModifier(URL url)
			throws ParserConfigurationException, SAXException, IOException {
		UniverseModifier universeModifier = new FluidParser().parse(url);
		builder.setUniverseModifier(universeModifier);
		createNew();
	}

	/**
	 * @see FluidHandler#simulate()
	 */
	@Override
	public void simulate() {
		simulator.simulate(universe);
	}

	/**
	 * @see FluidHandler#singleStepSimulation()
	 */
	@Override
	public void singleStepSimulation() {
		simulator.singleStepSimulation(universe);
	}

	/**
	 * @see FluidHandler#startSimulation()
	 */
	@Override
	public void startSimulation() {
		simulator.startSimulation();
	}
}
