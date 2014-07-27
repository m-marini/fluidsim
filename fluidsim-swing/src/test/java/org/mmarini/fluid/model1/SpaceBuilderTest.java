package org.mmarini.fluid.model1;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.mmarini.fluid.model1.Matchers.isVector;
import static org.mockito.Mockito.mock;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class SpaceBuilderTest {
	private static SpaceBuilder create() {
		final double r = 10e-3;
		final double w = r * (2 * 3 + 1);
		final double h = r * 2 / Math.sqrt(3) * 3 / 2 * 3;

		final SpaceTopology t = new SpaceTopology(r);
		final double m = FluidConstants.AMBIENT_AIR_MASS * t.getArea();
		final SpaceBuilder b = SpaceBuilder.newBuilder(new Rectangle2D.Double(
				0, 0, w, h), t, m);
		assertThat(b, notNullValue());
		assertThat(b, hasProperty("map", notNullValue()));
		assertTrue(b.getMap().isEmpty());

		final Map<Point, Cell> cells = b.getCells();
		assertThat(cells, notNullValue());
		assertThat(cells.size(), equalTo(4 * 4));
		assertThat(
				cells,
				hasEntry(
						equalTo(new Point()),
						allOf(hasProperty("mass", equalTo(m)),
								hasProperty("momentum", isVector(0, 0)))));
		return b;
	}

	@DataPoints
	public static int[] indexes = { -1, 0, 1 };

	@Test
	public void testFluid() {
		final SpaceBuilder b0 = create().setFluid(
				new Fluid(FluidConstants.AMBIENT_TEMPERATURE,
						FluidConstants.AIR_MOLECULAR_MASS));
		assertThat(b0, notNullValue());
		final Map<IdPair, TimeFunctor> m = b0.getMap();
		assertThat(m, notNullValue());
		assertThat(m.size(), equalTo(33));

		assertThat(m, hasKey(new IdPair(0, 0, 0, 1)));
		assertThat(m, hasKey(new IdPair(0, 0, 1, 0)));
		assertThat(m, hasKey(new IdPair(1, 0, 1, 1)));
		assertThat(m, hasKey(new IdPair(1, 0, 2, 0)));
		assertThat(m, hasKey(new IdPair(2, 0, 2, 1)));
		assertThat(m, hasKey(new IdPair(2, 0, 3, 0)));
		assertThat(m, hasKey(new IdPair(3, 0, 3, 1)));

		assertThat(m, hasKey(new IdPair(0, 1, 1, 0)));
		assertThat(m, hasKey(new IdPair(0, 1, 1, 1)));
		assertThat(m, hasKey(new IdPair(0, 1, 1, 2)));
		assertThat(m, hasKey(new IdPair(1, 1, 2, 0)));
		assertThat(m, hasKey(new IdPair(1, 1, 2, 1)));
		assertThat(m, hasKey(new IdPair(1, 1, 2, 2)));
		assertThat(m, hasKey(new IdPair(2, 1, 3, 0)));
		assertThat(m, hasKey(new IdPair(2, 1, 3, 1)));
		assertThat(m, hasKey(new IdPair(2, 1, 3, 2)));

		assertThat(m, hasKey(new IdPair(0, 2, 0, 1)));
		assertThat(m, hasKey(new IdPair(0, 2, 1, 2)));
		assertThat(m, hasKey(new IdPair(0, 2, 0, 3)));
		assertThat(m, hasKey(new IdPair(1, 2, 1, 1)));
		assertThat(m, hasKey(new IdPair(1, 2, 2, 2)));
		assertThat(m, hasKey(new IdPair(1, 2, 1, 3)));
		assertThat(m, hasKey(new IdPair(2, 2, 2, 1)));
		assertThat(m, hasKey(new IdPair(2, 2, 3, 2)));
		assertThat(m, hasKey(new IdPair(2, 2, 2, 3)));
		assertThat(m, hasKey(new IdPair(3, 2, 3, 1)));
		assertThat(m, hasKey(new IdPair(3, 2, 3, 3)));

		assertThat(m, hasKey(new IdPair(0, 3, 1, 2)));
		assertThat(m, hasKey(new IdPair(0, 3, 1, 3)));
		assertThat(m, hasKey(new IdPair(1, 3, 2, 2)));
		assertThat(m, hasKey(new IdPair(1, 3, 2, 3)));
		assertThat(m, hasKey(new IdPair(2, 3, 3, 2)));
		assertThat(m, hasKey(new IdPair(2, 3, 3, 3)));
	}

	@Test
	public void testNew() {
		assertThat(create(), notNullValue());
	}

	@Theory
	public void testPut(final int x, final int y, final int x1, final int y1) {
		final SpaceBuilder b0 = create();
		assertThat(b0, notNullValue());

		final TimeFunctor f = mock(TimeFunctor.class);
		final SpaceBuilder b1 = b0.put(new IdPair(x, y, x1, y1), f);
		assertThat(b1,
				hasProperty("map", hasEntry(new IdPair(x, y, x1, y1), f)));
	}

	@Theory
	public void testPut1(final int x, final int y, final int x1, final int y1) {
		final SpaceBuilder b0 = create();
		assertThat(b0, notNullValue());

		final TimeFunctor f = mock(TimeFunctor.class);
		final SpaceBuilder b1 = b0.put(new IdPair(x, y, x1, y1), f);
		assertThat(b1,
				hasProperty("map", hasEntry(new IdPair(x, y, x1, y1), f)));

		final TimeFunctor f1 = mock(TimeFunctor.class);
		final SpaceBuilder b2 = b1.put(new IdPair(x, y, x1, y1), f1);
		assertThat(b2,
				hasProperty("map", hasEntry(new IdPair(x, y, x1, y1), f1)));
	}

	@Theory
	public void testRemove(final int x, final int y, final int x1, final int y1) {
		final SpaceBuilder b0 = create();
		assertThat(b0, notNullValue());

		final TimeFunctor f = mock(TimeFunctor.class);
		final SpaceBuilder b1 = b0.put(new IdPair(x, y, x1, y1), f);
		assertThat(b1,
				hasProperty("map", hasEntry(new IdPair(x, y, x1, y1), f)));

		final SpaceBuilder b2 = b1.remove(new IdPair(x, y, x1, y1));
		assertThat(b2,
				hasProperty("map", not(hasKey(new IdPair(x, y, x1, y1)))));
	}

	@Theory
	public void testRemove1(final int x, final int y, final int x1, final int y1) {
		assumeThat(x, not(equalTo(0)));
		assumeThat(y, not(equalTo(0)));
		assumeThat(x1, not(equalTo(0)));
		assumeThat(y1, not(equalTo(0)));

		final SpaceBuilder b0 = create();
		assertThat(b0, notNullValue());

		final TimeFunctor f = mock(TimeFunctor.class);
		final SpaceBuilder b1 = b0.put(new IdPair(0, 0, 0, 0), f);
		assertThat(b1, hasProperty("map", hasEntry(new IdPair(0, 0, 0, 0), f)));

		final SpaceBuilder b2 = b1.remove(new IdPair(x, y, x1, y1));
		assertThat(b2, hasProperty("map", hasEntry(new IdPair(0, 0, 0, 0), f)));
		assertThat(b2,
				hasProperty("map", not(hasKey(new IdPair(x, y, x1, y1)))));
	}
}
