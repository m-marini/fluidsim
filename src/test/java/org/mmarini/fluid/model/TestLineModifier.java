/*
 * LineModifierTest.java
 *
 * $Id: LineModifierTestVert.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 *
 * 16/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.awt.Dimension;
import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LineModifierTestVert.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 * 
 */
public class TestLineModifier {
	private LineModifier lineModifier = new LineModifier();

	/**
	 * 
	 */
	public TestLineModifier() {
	}

	@Before
	public void setup() {
		lineModifier = new LineModifier();
		lineModifier.setX0(1);
		lineModifier.setY0(0);
		lineModifier.setX1(0.85);
		lineModifier.setY1(0.5);
	}

	/**
	 * Test method for
	 * {@link org.mmarini.fluid.model.LineModifier#modify(org.mmarini.fluid.model.Universe)}
	 * .
	 */
	@Test
	public void testModify() {
		UniverseMock universeMock = new UniverseMock();
		universeMock.setSize(new Dimension(10, 10));

		CellModifierMock cellModifierMock = new CellModifierMock();

		lineModifier.setCellModifier(cellModifierMock);
		lineModifier.setX0(1);
		lineModifier.setY0(0);
		lineModifier.setX1(0.85);
		lineModifier.setY1(0.5);
		lineModifier.modify(universeMock);
		assertThat(lineModifier, notNullValue());
		assertThat(
				cellModifierMock,
				hasProperty(
						"points",
						containsInAnyOrder(new Point(0, 9), new Point(1, 8),
								new Point(2, 9), new Point(3, 8), new Point(4,
										8), new Point(5, 7))));
	}
}
