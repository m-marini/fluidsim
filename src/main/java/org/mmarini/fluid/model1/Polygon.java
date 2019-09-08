package org.mmarini.fluid.model1;

import java.awt.geom.Point2D;

/**
 * 
 * @author us00852
 *
 */
public class Polygon {

	private final Point2D[] points;
	private final Material material;

	/**
	 * @param points
	 * @param material
	 */
	public Polygon(final Material material, final Point2D... points) {
		this.points = points;
		this.material = material;
	}

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @return the points
	 */
	public Point2D[] getPoints() {
		return points;
	}

}
