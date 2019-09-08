/**
 * 
 */
package org.mmarini.fluid.model1;

import java.awt.Point;

/**
 * @author us00852
 *
 */
public class SpaceTopology {
	private final double length;
	private final double area;

	/**
	 * @param length
	 */
	public SpaceTopology(final double length) {
		this.length = length;
		area = length * length;
	}

	/**
	 * 
	 * @return
	 */
	public double getArea() {
		return area;
	}

	/**
	 * @return the length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public Vector2d getVector(final Point p) {
		return new Vector2d(p.x * length, p.y * length);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SpaceTopology [radius=").append(length).append("]");
		return builder.toString();
	}
}
