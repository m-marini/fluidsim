package org.mmarini.fluid.model1;

import java.awt.Point;
import java.util.Collections;
import java.util.Map;

/**
 * 
 * @author us00852
 *
 */
public class Simulation {
	private final Map<Point, Cell> cells;
	private final TimeFunctor[] functors;

	/**
	 * @param cells
	 * @param functors
	 */
	public Simulation(final Map<Point, Cell> cells, final TimeFunctor[] functors) {
		this.cells = cells;
		this.functors = functors;
	}

	/**
	 * 
	 * @param dt
	 * @return
	 */
	public Simulation apply(final double dt) {
		for (final TimeFunctor f : functors)
			f.apply(dt);
		for (final Cell c : cells.values())
			c.apply();
		return this;
	}

	/**
	 * @return the cells
	 */
	public Map<Point, Cell> getCells() {
		return Collections.unmodifiableMap(cells);
	}

	/**
	 * @return the functors
	 */
	public TimeFunctor[] getFunctors() {
		return functors;
	}
}
