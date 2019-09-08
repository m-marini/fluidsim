package org.mmarini.fluid.model1;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasProperty;

import org.hamcrest.Matcher;

public class Matchers {

	public static Matcher<? super Vector2d> isVector(final double x,
			final double y) {
		return allOf(hasProperty("x", closeTo(x, 1e-6)),
				hasProperty("y", closeTo(y, 1e-6)));
	}

}
