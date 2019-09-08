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

import org.mmarini.fluid.xml.FluidParser;
import org.xml.sax.SAXException;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.PublishSubject;

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
public class FluidSimulatorImpl implements FluidSimulator {
	private static final long MIN_INTERVAL = 80;
	private static final double SIMULATION_RATE = 1.0;
	private static final double SINGLE_STEP_INTERVAL = 0.1;

	private final PublishSubject<Universe> universeSubj;
	private final PublishSubject<Double> rateSubj;
	private final Simulator simulator;
	private final UniverseBuilderImpl builder;
	private Universe previous;

	public FluidSimulatorImpl() {
		super();
		simulator = new Simulator(SINGLE_STEP_INTERVAL, SIMULATION_RATE, MIN_INTERVAL);
		builder = new UniverseBuilderImpl();
		universeSubj = PublishSubject.create();
		rateSubj = PublishSubject.create();
	}

	@Override
	public FluidSimulator createNew() {
		previous = builder.build();
		universeSubj.onNext(previous);
		return this;
	}

	@Override()
	public Flowable<Double> getRateFlow() {
		return rateSubj.toFlowable(BackpressureStrategy.LATEST);
	}

	@Override()
	public Flowable<Universe> getUniverseFlow() {
		return universeSubj.toFlowable(BackpressureStrategy.LATEST);
	}

	@Override
	public FluidSimulator loadUniverseModifier(final URL resource)
			throws ParserConfigurationException, SAXException, IOException {
		final UniverseModifier universeModifier = new FluidParser().parse(resource);
		builder.setUniverseModifier(universeModifier);
		return createNew();
	}

	/**
	 * Performs a single step simulation.
	 */
	@Override()
	public FluidSimulator single() {
		simulator.simulate(previous);
		final double rate = 1000. * simulator.getLastCount() / simulator.getLastElapsed();
		universeSubj.onNext(previous);
		rateSubj.onNext(rate);
		return this;
	}

	/**
	 * Starts the simulation.
	 */
	@Override()
	public FluidSimulator start() {
		throw new Error("Not implemented");
	}

	/**
	 * Starts the simulation.
	 */
	@Override()
	public FluidSimulator stop() {
		throw new Error("Not implemented");
	}
}
