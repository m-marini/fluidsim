/**
 * 
 */
package org.mmarini.fluid.model1;

import java.awt.Dimension;

/**
 * @author us00852
 *
 */
public class FluidSpace {
	private final Fluid fluid;
	private final SpaceTopology topology;

	/**
	 * @param fluid
	 * @param topology
	 */
	public FluidSpace(final Fluid fluid, final SpaceTopology topology) {
		this.fluid = fluid;
		this.topology = topology;
	}

	/**
	 * @return the fluid
	 */
	public Fluid getFluid() {
		return fluid;
	}

	public Dimension getGridSize() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the topology
	 */
	public SpaceTopology getTopology() {
		return topology;
	}

}
