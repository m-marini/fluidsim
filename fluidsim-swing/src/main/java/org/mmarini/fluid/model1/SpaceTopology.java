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
	private static final double K_AREA = Math.sqrt(3) * 3;
	private static final double K_EDGE = 2 / Math.sqrt(3);
	private final double radius;
	private final double edge;
	private final double area;

	/**
	 * @param radius
	 */
	public SpaceTopology(final double radius) {
		this.radius = radius;
		edge = radius * K_EDGE;
		area = radius * radius * K_AREA;
	}

	/**
	 * 
	 * @return
	 */
	public double getArea() {
		return area;
	}

	/**
	 * 
	 * @return
	 */
	public double getEdge() {
		return edge;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public Vector2d getVector(final Point p) {
		return p.y % 2 == 0 ? new Vector2d(p.x * 2 * radius, p.y * edge * 1.5)
				: new Vector2d((p.x * 2 + 1) * radius, p.y * edge * 1.5);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SpaceTopology [radius=").append(radius).append("]");
		return builder.toString();
	}
}
