package org.mmarini.fluid.model1;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.mmarini.fluid.model1.Matchers.isVector;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class Vector2dTest {
	@DataPoints
	public static double[] coords = { -10, -2, 0, 2, 10 };

	@Theory
	public void testAdd(final double x0, final double y0, final double x1,
			final double y1) {
		assertThat(new Vector2d(x0, y0).add(new Vector2d(x1, y1)),
				isVector(x0 + x1, y0 + y1));
	}

	@Theory
	public void testEquals(final double x, final double y) {
		final Vector2d v0 = new Vector2d(x, y);
		final Vector2d v1 = new Vector2d(x, y);
		assertTrue(v0.equals(v1));
		assertTrue(v1.equals(v0));
	}

	@Theory
	public void testHashCode(final double x, final double y) {
		final int h0 = new Vector2d(x, y).hashCode();
		final int h1 = new Vector2d(x, y).hashCode();
		assertThat(h0, equalTo(h1));
	}

	@Theory
	public void testLength(final double x0, final double y0, final double x1,
			final double y1) {
		final double v = new Vector2d(x0, y0).getLength();
		assertThat(v, closeTo(Math.sqrt(x0 * x0 + y0 * y0), 1e-6));
	}

	@Theory
	public void testMul(final double x0, final double y0, final double l) {
		assertThat(new Vector2d(x0, y0).mul(l), isVector(x0 * l, y0 * l));
	}

	@Theory
	public void testMul(final double x0, final double y0, final double x1,
			final double y1) {
		assertThat(new Vector2d(x0, y0).mul(new Vector2d(x1, y1)),
				closeTo(x0 * x1 + y0 * y1, 1e-6));
	}

	@Test
	public void testNewVector() {
		final Vector2d v = Vector2d.ZERO;
		assertThat(v.getX(), equalTo(0.0));
		assertThat(v.getY(), equalTo(0.0));
	}

	@Theory
	public void testNewVector(final double x, final double y) {
		final Vector2d v = new Vector2d(x, y);
		assertThat(v.getX(), equalTo(x));
		assertThat(v.getY(), equalTo(y));
	}

	@Theory
	public void testNotEquals(final double x0, final double y0,
			final double x1, final double y1) {
		assumeThat(x0, not(equalTo(x1)));
		assumeThat(y0, not(equalTo(y1)));
		final Vector2d v0 = new Vector2d(x0, y0);
		final Vector2d v1 = new Vector2d(x1, y1);
		assertFalse(v0.equals(v1));
		assertFalse(v1.equals(v0));
	}

	@Theory
	public void testSquare(final double x0, final double y0, final double x1,
			final double y1) {
		final double v = new Vector2d(x0, y0).getSquare();
		assertThat(v, closeTo(x0 * x0 + y0 * y0, 1e-6));
	}

	@Theory
	public void testSub(final double x0, final double y0, final double x1,
			final double y1) {
		assertThat(new Vector2d(x0, y0).sub(new Vector2d(x1, y1)),
				isVector(x0 - x1, y0 - y1));
	}

	@Theory
	public void testToString(final double x, final double y) {
		assertThat(new Vector2d(x, y), hasToString("(" + x + ", " + y + ")"));
	}
}
