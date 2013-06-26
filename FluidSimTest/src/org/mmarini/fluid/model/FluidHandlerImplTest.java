/*
 * FluidHandlerImplTest.java
 *
 * $Id: FluidHandlerImplTest.java,v 1.2 2007/08/08 23:57:24 marco Exp $
 *
 * 07/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * @author US00852
 * 
 */
public class FluidHandlerImplTest extends
		AbstractDependencyInjectionSpringContextTests {

	// private static final double TIME = 0.1;

	protected FluidHandlerImpl fluidHandler;

	/**
         * 
         */
	public FluidHandlerImplTest() {
		setPopulateProtectedVariables(true);
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { getClass().getName().replace('.', '/') + ".xml" };
	}

	/**
	 * Test method for
	 * {@link org.mmarini.fluid.model.FluidHandlerImpl#simulate(double)}.
	 */
	public void testSimulate() {
		assertNotNull(fluidHandler);
		fluidHandler.singleStepSimulation();
	}
}
