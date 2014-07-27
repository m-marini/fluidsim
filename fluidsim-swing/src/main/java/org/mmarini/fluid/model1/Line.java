package org.mmarini.fluid.model1;

import java.awt.geom.Point2D;

/**
 * 
 * @author us00852
 *
 */
public class Line implements FluidShape {

	private final Point2D start;
	private final Point2D end;

	/**
	 * @param start
	 * @param end
	 */
	public Line(final Point2D start, final Point2D end) {
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
	 * @return the start
	 */
	public Point2D getStart() {
		return start;
	}

}
