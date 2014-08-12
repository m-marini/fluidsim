package org.mmarini.fluid.model1;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mmarini.fluid.model1.Matchers.isVector;

import java.awt.Point;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class SpaceTopologyTest {
	@DataPoints
	public static double[] lengths = { 1, 2, 10 };
	@DataPoints
	public static int[] index = { -2, -1, 0, 1, 2 };

	@Theory
	public void testVector(final int x, final int y, final double length) {
		assertThat(new SpaceTopology(length).getVector(new Point(x, y)),
				isVector(x * length, y * length));
	}

	@Theory
	public void testGetArea(final double length) {
		final SpaceTopology s = new SpaceTopology(length);
		assertThat(s.getArea(), closeTo(length * length, 1e-6));
	}

	@Theory
	public void testNew(final double length) {
		final SpaceTopology s = new SpaceTopology(length);
		assertThat(s.getLength(), equalTo(length));
	}

}
