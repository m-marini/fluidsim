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

		return new SpaceBuilder(space, topology, null, c,
				Collections.<Point, TimeFunctor> emptyMap(),
				Collections.<IdPair, TimeFunctor> emptyMap());
	}

	private final Rectangle2D space;
	private final Fluid fluid;
	private final SpaceTopology topology;
	private final Map<Point, Cell> cells;
	private final Map<IdPair, TimeFunctor> pairMap;
	private final Map<Point, TimeFunctor> cellMap;

	/**
	 * @param spaceDef
	 * @param topology
	 * @param cells
	 * @param map
	 */
	private SpaceBuilder(final Rectangle2D space, final SpaceTopology topology,
			final Fluid fluid, final Map<Point, Cell> cells,
			final Map<Point, TimeFunctor> cellMap,
			final Map<IdPair, TimeFunctor> pairMap) {
		this.space = space;
		this.topology = topology;
		this.fluid = fluid;
		this.cells = cells;
		this.pairMap = pairMap;
		this.cellMap = cellMap;
	}

	/**
	 * 
	 * @return
	 */
	public TimeFunctor[] create() {
		return pairMap.values().toArray(new TimeFunctor[0]);
	}

	/**
	 * @return the cellMap
	 */
	public Map<Point, TimeFunctor> getCellMap() {
		return Collections.unmodifiableMap(cellMap);
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
	public Map<IdPair, TimeFunctor> getPairMap() {
		return Collections.unmodifiableMap(pairMap);
	}

	/**
	 * 
	 * @param fluid2
	 * @return
	 */
	public SpaceBuilder setFluid(final Fluid fluid2) {
		final double kt = topology.getEdge() / topology.getArea();
		final Map<IdPair, TimeFunctor> pm = new HashMap<IdPair, TimeFunctor>();
		final Map<Point, TimeFunctor> cm = new HashMap<Point, TimeFunctor>();

		// C = R T / (pm v) S(i,j)
		final double c1 = FluidConstants.R * fluid2.getMolecularMass()
				/ fluid2.getMolecularMass() / topology.getArea()
				* topology.getEdge();

		for (final Entry<Point, Cell> e : cells.entrySet()) {
			final Cell ci = e.getValue();
			for (final IdPair i : IdPair.createAdjacents(e.getKey())) {
				final Cell cj = cells.get(i.getId1());

				cm.put(i.getId0(), new TimeFunctor() {

					@Override
					public void apply(final double dt) {
						// dQ(i) = g(i) m(i) dt
						ci.addMomentum(FluidConstants.STANDARD_GRAVITY.mul(ci
								.getMass() * dt));
					}
				});
				if (cj != null) {
					final Vector2d n = i.getVector(topology).getVersor();
					pm.put(i, new TimeFunctor() {

						@Override
						public void apply(final double dt) {
							/*
							 * k(i,j) = -(V(j) - V(i) ) N(i,j) S(i,j) / v
							 */
							final double kij = -cj.getVelocity()
									.sub(ci.getVelocity()).mul(n)
									* kt;

							final double mi = ci.getMass();
							final double mj = cj.getMass();

							// dm(i,j) = k(i,j) m(i) dt
							ci.addMass(kij * mi * dt);
							// dm(j,i) = k(i,j) m(j) dt
							cj.addMass(kij * mj * dt);

							// F(i,j) = F(j,i) = ( m(j) - m(i) ) C N(i,j)
							final Vector2d fij = n.mul((mj - mi) * c1);

							// dQ(i,j) = Q(i) k(i,j) dt^2 + F(i,j) dt
							ci.addMomentum(ci.getMomentum().mul(kij * dt * dt)
									.add(fij.mul(dt)));
							// dQ(j,i) = Q(j) k(i,j) dt^2 + F(i,j) dt
							cj.addMomentum(cj.getMomentum().mul(kij * dt * dt)
									.add(fij.mul(dt)));
						}
					});
				}
			}
		}
		return new SpaceBuilder(space, topology, fluid2, cells, cm, pm);
	}
}
