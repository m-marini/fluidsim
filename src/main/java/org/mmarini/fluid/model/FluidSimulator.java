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

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import io.reactivex.rxjava3.core.Flowable;

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
public interface FluidSimulator {

	public abstract FluidSimulator createNew();

	public abstract Flowable<Double> getRateFlow();

	public abstract Flowable<Universe> getUniverseFlow();

	public abstract FluidSimulator loadUniverseModifier(URL resource)
			throws ParserConfigurationException, SAXException, IOException;

	/**
	 * Performs a single step simulation.
	 */
	public abstract FluidSimulator single();

	/**
	 * Starts the simulation.
	 */
	public abstract FluidSimulator start();

	/**
	 * Starts the simulation.
	 */
	public abstract FluidSimulator stop();
}
