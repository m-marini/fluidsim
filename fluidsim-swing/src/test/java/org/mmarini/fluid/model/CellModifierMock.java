/*
 * CellModifierMock.java
 *
 * $Id: CellModifierMock.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 *
 * 16/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: CellModifierMock.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 * 
 */
public class CellModifierMock implements CellModifier {
	private List<Point> points;

	/**
	 * 
	 */
	public CellModifierMock() {
		points = new ArrayList<Point>();
	}

	/**
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * @see org.mmarini.fluid.model.CellModifier#modify(org.mmarini.fluid.model.Universe,
	 *      int, int)
	 */
	public void modify(Universe universe, int i, int j) {
		points.add(new Point(i, j));
	}

}
