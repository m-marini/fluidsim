package org.mmarini.fluid.model1;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;
import static org.mmarini.fluid.model1.Matchers.isVector;

import java.awt.Point;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class SpaceTopologyTest {
	@DataPoints
	public static double[] radius = { 1, 2, 10 };
	@DataPoints
	public static int[] index = { -2, -1, 0, 1, 2 };

	@Theory
	public void testEvenVector(final int x, final int y, final double radius) {
		assumeTrue(y % 2 == 0);
		assertThat(new SpaceTopology(radius).getVector(new Point(x, y)),
				isVector(x * radius * 2, y * radius * Math.sqrt(3)));
	}

	@Theory
	public void testGetArea(final double radius) {
		final SpaceTopology s = new SpaceTopology(radius);
		assertThat(s.getArea(),
				closeTo(radius * radius * 6 * Math.cos(Math.PI / 6), 1e-6));
	}

	@Theory
	public void testGetEdge(final double radius) {
		final SpaceTopology s = new SpaceTopology(radius);
		assertThat(s.getEdge(), closeTo(radius * 2 / Math.sqrt(3), 1e-6));
	}

	@Theory
	public void testNew(final double radius) {
		final SpaceTopology s = new SpaceTopology(radius);
		assertThat(s.getRadius(), equalTo(radius));
	}

	@Theory
	public void testOddVector(final int x, final int y, final double radius) {
		assumeTrue(y % 2 == 1);
		assertThat(new SpaceTopology(radius).getVector(new Point(x, y)),
				isVector((x * 2 + 1) * radius, y * radius * Math.sqrt(3)));
	}

}
