/**
 * 
 */
package org.mmarini.fluid.model1;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author us00852
 *
 */
public class SpaceBuilder {

	/**
	 * 
	 * @param space
	 * @param topology
	 * @param fluid
	 * @param mass
	 * @return
	 */
	public static final SpaceBuilder newBuilder(final Rectangle2D space,
			final SpaceTopology topology, final double mass) {
		final Map<IdPair, TimeFunctor> m = new HashMap<IdPair, TimeFunctor>();

		final double w = space.getWidth();
		final double h = space.getHeight();
		final double r = topology.getRadius();
		final double e = topology.getEdge();

		final int nr = (int) Math.ceil(h * 2 / e / 3 + 1);
		final int nc = (int) Math.ceil(0.5 * (w / r + 1));
		final Map<Point, Cell> c = new HashMap<Point, Cell>();

		for (int i = 0; i < nr; ++i)
			for (int j = 0; j < nc; ++j)
				c.put(new Point(j, i), new Cell(mass, Vector2d.ZERO));

		return new SpaceBuilder(space, topology, null, c, m);
	}

	private final Rectangle2D space;
	private final Fluid fluid;
	private final SpaceTopology topology;
	private final Map<Point, Cell> cells;
	private final Map<IdPair, TimeFunctor> map;

	/**
	 * @param spaceDef
	 * @param topology
	 * @param cells
	 * @param map
	 */
	private SpaceBuilder(final Rectangle2D space, final SpaceTopology topology,
			final Fluid fluid, final Map<Point, Cell> cells,
			final Map<IdPair, TimeFunctor> map) {
		this.space = space;
		this.topology = topology;
		this.fluid = fluid;
		this.cells = cells;
		this.map = map;
	}

	/**
	 * 
	 * @return
	 */
	public TimeFunctor[] create() {
		return map.values().toArray(new TimeFunctor[0]);
	}

	/**
	 * @return the cells
	 */
	public Map<Point, Cell> getCells() {
		return Collections.unmodifiableMap(cells);
	}

	/**
	 * @return the map
	 */
	public Map<IdPair, TimeFunctor> getMap() {
		return Collections.unmodifiableMap(map);
	}

	/**
	 * 
	 * @param k
	 * @param f
	 * @return
	 */
	public SpaceBuilder put(final IdPair k, final TimeFunctor f) {
		final Map<IdPair, TimeFunctor> m = new HashMap<IdPair, TimeFunctor>(map);
		m.put(k, f);
		return new SpaceBuilder(space, topology, fluid, cells, m);
	}

	/**
	 * 
	 * @param k
	 * @return
	 */
	public SpaceBuilder remove(final IdPair k) {
		final Map<IdPair, TimeFunctor> m = new HashMap<IdPair, TimeFunctor>(map);
		m.remove(k);
		return new SpaceBuilder(space, topology, fluid, cells, m);
	}

	/**
	 * 
	 * @param fluid2
	 * @return
	 */
	public SpaceBuilder setFluid(final Fluid fluid2) {
		final double kt = topology.getEdge() / topology.getArea();
		final Map<IdPair, TimeFunctor> m = new HashMap<IdPair, TimeFunctor>();
		for (final Entry<Point, Cell> e : cells.entrySet()) {
			final Cell ci = e.getValue();
			for (final IdPair i : IdPair.createAdjacents(e.getKey())) {
				final Cell cj = cells.get(i.getId1());
				if (cj != null) {
					final Vector2d normal = i.getVector(topology).getVersor();
					m.put(i, new TimeFunctor() {

						@Override
						public void apply(final double dt) {
							final double kij = -cj.getVelocity()
									.sub(ci.getVelocity()).mul(normal)
									* kt;
						}
					});
				}
			}
		}
		return new SpaceBuilder(space, topology, fluid2, cells, m);
	}
}
