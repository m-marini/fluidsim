/**
 *
 */
package org.mmarini.fluid;

import static org.hamcrest.Matchers.closeTo;

import org.hamcrest.Matcher;

/**
 * @author us00852
 *
 */
public class TestUtils {
	final static private double EPSILON = 1e-3;

	/**
	 * Returns a matcher of Double that matches a double within a 0.001 relative
	 * range
	 *
	 * @param value
	 * @return
	 */
	public static Matcher<Double> around(final double value) {
		return around(value, EPSILON);
	}

	/**
	 * Returns a matcher of Double that matches a double within a relative range
	 * epsilon
	 *
	 * @param value
	 * @param epsilon
	 * @return
	 */
	public static Matcher<Double> around(final double value, final double epsilon) {
		return closeTo(value, Math.abs(value * epsilon));
	}
}
