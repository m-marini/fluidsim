package org.mmarini.fluid.model1;

import java.awt.geom.Point2D;

/**
 * 
 * @author us00852
 *
 */
public class Line {

	private final Point2D start;
	private final Point2D end;
	private final Material material;

	/**
	 * @param material
	 * @param start
	 * @param end
	 */
	public Line(final Material material, final Point2D start, final Point2D end) {
		this.material = material;
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the end
	 */
	public Point2D getEnd() {
		return end;
	}

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @return the start
	 */
	public Point2D getStart() {
		return start;
	}

}
