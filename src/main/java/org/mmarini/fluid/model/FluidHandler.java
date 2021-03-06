/*
 * FluidHandler.java
 *
 * $Id: FluidHandler.java,v 1.4 2007/08/18 08:29:54 marco Exp $
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

import org.xml.sax.SAXException;

/**
 * This is the interface of the fluid handler.
 * <p>
 * The FluidHandler declares the method of the simulation use cases.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: FluidHandler.java,v 1.4 2007/08/18 08:29:54 marco Exp $
 *
 */
public interface FluidHandler {

	/**
	 * Creates a new universe.
	 */
	public abstract void createNew();

	/**
	 * Returns the cell value.
	 *
	 * @param i the row index
	 * @param j the colum index
	 * @return the value
	 */
	public abstract double getCellValue(int i, int j);

	/**
	 * Returns the flux of a cell
	 *
	 * @param i the row index
	 * @param j the column index
	 * @return the flux value
	 */
	public abstract double getFluxValue(int i, int j);

	/**
	 * Returns the relation value.
	 *
	 * @param i the row index
	 * @param j the column index
	 * @return the value
	 */
	public abstract double getRelationValue(int i, int j);

	/**
	 * Returns the simulation rate
	 *
	 * @return the simulation rate in steps/sec
	 */
	public abstract double getSimulationRate();

	/**
	 * Returns the size of universe.
	 *
	 * @return the size
	 */
	public abstract Dimension getSize();

	/**
	 *
	 * @param resource
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public abstract void loadUniverseModifier(URL resource)
			throws ParserConfigurationException, SAXException, IOException;

	/**
	 * Performs a simulation cycle.
	 */
	public abstract void simulate();

	/**
	 * Performs a single step simulation.
	 */
	public abstract void singleStepSimulation();

	/**
	 * Starts the simulation.
	 */
	public abstract void startSimulation();
}
