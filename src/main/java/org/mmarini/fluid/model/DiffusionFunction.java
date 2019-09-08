/*
 * DiffusionFunction.java
 *
 * $Id: DiffusionFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * @author marco
 * @version $Id: DiffusionFunction.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public class DiffusionFunction implements CoefficientFunction {
	private double diffusion;

	/**
	 *
	 */
	public DiffusionFunction() {
	}

	/**
	 * @param diffusion
	 */
	public DiffusionFunction(final double diffusion) {
		this.diffusion = diffusion;
	}

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getA(org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getA(final TimeContext timeContext) {
		return diffusion;
	}

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getB(org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getB(final TimeContext timeContext) {
		return 0;
	}

	/**
	 * @see org.mmarini.fluid.model.CoefficientFunction#getC(org.mmarini.fluid.model.TimeContext)
	 */
	@Override
	public double getC(final TimeContext timeContext) {
		return 0;
	}

	/**
	 * @return the diffusion
	 */
	public double getDiffusion() {
		return diffusion;
	}

	/**
	 * @param diffusion the diffusion to set
	 */
	public void setDiffusion(final double diffusion) {
		this.diffusion = diffusion;
	}

}
