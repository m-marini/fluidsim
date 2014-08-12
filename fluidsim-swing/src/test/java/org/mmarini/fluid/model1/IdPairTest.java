package org.mmarini.fluid.model1;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.mmarini.fluid.model1.Matchers.isVector;

import java.awt.Point;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class IdPairTest {
	@DataPoints
	public static int[] indexes = { 0, 1, 2 };

	@Theory
	public void testEqual(final int i0, final int j0, final int i1, final int j1) {
		assertTrue(new IdPair(new Point(i0, j0), new Point(j1, j0))
				.equals(new IdPair(new Point(i0, j0), new Point(j1, j0))));
	}

	@Theory
	public void testHashcode(final int i0, final int j0, final int i1,
			final int j1) {
		assertThat(new IdPair(new Point(i0, j0), new Point(j1, j0)).hashCode(),
				equalTo(new IdPair(new Point(i0, j0), new Point(j1, j0))
						.hashCode()));
	}

	@Theory
	public void testNew(final int i0, final int j0, final int i1, final int j1) {
		final IdPair p = new IdPair(new Point(i0, j0), new Point(i1, j1));
		assertThat(p.getId0(), equalTo(new Point(i0, j0)));
		assertThat(p.getId1(), equalTo(new Point(i1, j1)));
	}

	@Theory
	public void testNew1(final int i0, final int j0, final int i1, final int j1) {
		final IdPair p = new IdPair(i0, j0, i1, j1);
		assertThat(p.getId0(), equalTo(new Point(i0, j0)));
		assertThat(p.getId1(), equalTo(new Point(i1, j1)));
	}

	@Theory
	public void testNotEqual(final int i0, final int j0, final int i1,
			final int j1, final int i2, final int j2, final int i3, final int j3) {
		assumeThat(i0, not(equalTo(i2)));
		assumeThat(j0, not(equalTo(j2)));
		assumeThat(i1, not(equalTo(i3)));
		assumeThat(j1, not(equalTo(j3)));
		final IdPair p0 = new IdPair(new Point(i0, j0), new Point(j1, j0));
		final IdPair p1 = new IdPair(new Point(i2, j2), new Point(j3, j3));
		assertFalse(p0.equals(p1));
	}

	@Theory
	public void testAdjacents(final int x, final int y) {
		assertThat(
				IdPair.createAdjacents(new Point(x, y)),
				arrayContainingInAnyOrder(new IdPair(x, y, x + 1, y),
						new IdPair(x, y, x, y + 1)));
	}

	@Theory
	public void testVector(final int x, final int y) {
		final double length = 1f;
		assertThat(
				new IdPair(x, y, x + 1, y).getVector(new SpaceTopology(length)),
				isVector(length, 0));
		assertThat(
				new IdPair(x, y, x, y + 1).getVector(new SpaceTopology(length)),
				isVector(0, length));
	}
}