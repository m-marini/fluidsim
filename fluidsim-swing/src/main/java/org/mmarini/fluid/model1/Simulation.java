package org.mmarini.fluid.model1;

/**
 * 
 * @author us00852
 *
 */
public class Simulation {
	private final Cell[][] cells;
	private final TimeFunctor[] functors;

	/**
	 * @param cells
	 * @param functors
	 */
	public Simulation(final Cell[][] cells, final TimeFunctor[] functors) {
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
		for (final Cell[] r : cells)
			for (final Cell c : r)
				c.apply();
		return this;
	}

	/**
	 * @return the cells
	 */
	public Cell[][] getCells() {
		return cells;
	}
}
