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
         * @see org.mmarini.fluid.model.CoefficientFunction#getA(org.mmarini.fluid.model.TimeContext)
         */
    public double getA(TimeContext timeContext) {
	return getDiffusion();
    }

    /**
         * @see org.mmarini.fluid.model.CoefficientFunction#getB(org.mmarini.fluid.model.TimeContext)
         */
    public double getB(TimeContext timeContext) {
	return 0;
    }

    /**
         * @see org.mmarini.fluid.model.CoefficientFunction#getC(org.mmarini.fluid.model.TimeContext)
         */
    public double getC(TimeContext timeContext) {
	return 0;
    }

    /**
         * @return the diffusion
         */
    private double getDiffusion() {
	return diffusion;
    }

    /**
         * @param diffusion
         *                the diffusion to set
         */
    public void setDiffusion(double diffusion) {
	this.diffusion = diffusion;
    }

}
