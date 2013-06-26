/*
 * LineModifierTest.java
 *
 * $Id: LineModifierTest.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 *
 * 16/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: LineModifierTest.java,v 1.1 2007/08/18 08:28:29 marco Exp $
 * 
 */
public class LineModifierTest extends
	AbstractDependencyInjectionSpringContextTests {
    protected LineModifier lineModifier;

    protected Universe universeMock;

    /**
         * 
         */
    public LineModifierTest() {
	setPopulateProtectedVariables(true);
    }

    @Override
    protected String[] getConfigLocations() {
	return new String[] { getClass().getName().replace('.', '/') + ".xml" };
    }

    /**
         * Test method for
         * {@link org.mmarini.fluid.model.LineModifier#modify(org.mmarini.fluid.model.Universe)}.
         */
    public void testModify() {
	assertNotNull(lineModifier);
	assertNotNull(universeMock);
	lineModifier.modify(universeMock);
    }

}
