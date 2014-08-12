package org.mmarini.fluid.model1;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mmarini.fluid.model1.Matchers.isVector;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class SpaceBuilderTest {

	private static SpaceBuilder create() {
		final double r = 10e-3;
		final double w = r;
		final double h = r;

		final SpaceTopology t = new SpaceTopology(r);
		final double m = FluidConstants.AMBIENT_AIR_MASS * t.getArea();
		final SpaceBuilder b = SpaceBuilder.newBuilder(new Rectangle2D.Double(
				0, 0, w, h), t, m);
		assertThat(b, notNullValue());
		assertThat(b, hasProperty("pairMap", notNullValue()));
		assertTrue(b.getPairMap().isEmpty());
		assertThat(b, hasProperty("cellMap", notNullValue()));
		assertTrue(b.getPairMap().isEmpty());

		final Map<Point, Cell> cells = b.getCells();
		assertThat(cells, notNullValue());
		assertThat(cells.size(), equalTo(4));
		assertThat(
				cells,
				hasEntry(
						equalTo(new Point()),
						allOf(hasProperty("mass", equalTo(m)),
								hasProperty("momentum", isVector(0, 0)))));
		return b;
	}

	private static final double EPSILON = 1e-6;

	@DataPoints
	public static int[] indexes = { -1, 0, 1 };

	@Test
	public void testCreate() {
		final SpaceBuilder b0 = create().setFluid(
				new Fluid(FluidConstants.AMBIENT_TEMPERATURE,
						FluidConstants.AIR_MOLECULAR_MASS));
		assertThat(b0, notNullValue());

		final Simulation s = b0.createSimulation();
		assertThat(s, notNullValue());

		final Map<Point, Cell> c = s.getCells();
		assertThat(c, notNullValue());
		assertThat(c.size(), equalTo(4));

		final TimeFunctor[] f = s.getFunctors();
		assertThat(f, notNullValue());
		assertThat(f, arrayWithSize(8));
	}

	@Test
	public void testFluid() {
		final SpaceBuilder b0 = create().setFluid(
				new Fluid(FluidConstants.AMBIENT_TEMPERATURE,
						FluidConstants.AIR_MOLECULAR_MASS));
		assertThat(b0, notNullValue());

		final Map<Point, TimeFunctor> cm = b0.getCellMap();
		assertThat(cm, notNullValue());
		assertThat(cm.size(), equalTo(4));

		assertThat(cm, hasKey(new Point(0, 0)));
		assertThat(cm, hasKey(new Point(0, 1)));

		assertThat(cm, hasKey(new Point(1, 0)));
		assertThat(cm, hasKey(new Point(1, 1)));

		final Map<IdPair, TimeFunctor> pm = b0.getPairMap();
		assertThat(pm, notNullValue());
		assertThat(pm.size(), equalTo(4));

		assertThat(pm, hasKey(new IdPair(0, 0, 0, 1)));
		assertThat(pm, hasKey(new IdPair(0, 0, 1, 0)));
		
		assertThat(pm, hasKey(new IdPair(0, 1, 1, 1)));
		assertThat(pm, hasKey(new IdPair(1, 0, 1, 1)));
	}

	@Test
	public void testNew() {
		assertThat(create(), notNullValue());
	}

	@Test
	public void testSimulation() {
		final double r = 10e-3;
		final double w = r;
		final double h = 0;
		// final double h = r * 2 / Math.sqrt(3) * 3 / 2;

		final SpaceTopology t = new SpaceTopology(r);
		final double m = FluidConstants.AMBIENT_AIR_MASS * t.getArea();
		final Simulation s = SpaceBuilder
				.newBuilder(new Rectangle2D.Double(0, 0, w, h), t, m)
				.setFluid(
						new Fluid(FluidConstants.AMBIENT_TEMPERATURE,
								FluidConstants.AIR_MOLECULAR_MASS))
				.createSimulation().apply(1e-3).apply(1e-3);
		assertThat(s, notNullValue());

		final Map<Point, Cell> c = s.getCells();
		assertThat(c, notNullValue());
		assertThat(c.size(), equalTo(2));

		assertThat(s, hasProperty("functors", arrayWithSize(2 + 1)));

		final double m0 = t.getArea() * FluidConstants.AMBIENT_AIR_MASS;
		assertThat(
				c,
				hasEntry(equalTo(new Point(0, 0)),
						hasProperty("mass", closeTo(m0, EPSILON))));
		assertThat(
				c,
				hasEntry(equalTo(new Point(0, 0)),
						hasProperty("momentum", isVector(0, 0))));
	}
}
