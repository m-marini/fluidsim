/*
 * FluidFunction.java
 *
 * $Id: FluidFunction.java,v 1.5 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The FluidFunction simulates the fluid effect.
 * <p>
 * The difference of value between two adjacent cells determine a pressure
 * between the cells. This pressure determines an acceleration of the values
 * exchanged. The change of the flux is proportianal to the pressure and the
 * time, in the same time the flux is descreased proportionaly the time because
 * of viscosity.<br>
 * The flux difference is incoming the cell if the adiacent cells have greater
 * values.
 * <p>
 * &rho;(t+&Delta;t)=&gamma;(t) * k * &Delta;t + &rho;(t) * (1 - kv * &Delta;t)
 * </p>
 * <p>
 * <ul>
 * <li>a = k * &Delta;t</li>
 * <li>b = 1 - kv * &Delta;t</li>
 * <li>c = 0</li>
 * </ul>
 * </p>
 * <p>
 * The constant kv determines the viscosity of material. A 0 value is a material
 * without viscosity.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: FluidFunction.java,v 1.5 2007/08/18 08:29:54 marco Exp $
 * 
 */
public class FluidFunction extends ElasticFunction {
    private double viscosity;

    /**
         * @see CoefficientFunction#getB(TimeContext)
         */
    public double getB(TimeContext timeContext) {
	return 1 - getViscosity() * timeContext.getDeltaTime();
    }

    /**
         * Returns the viscosity constant.
         * 
         * @return the viscosity
         */
    public double getViscosity() {
	return viscosity;
    }

    /**
         * Sets the viscosity constant.
         * 
         * @param viscosity
         *                the viscosity to set
         */
    public void setViscosity(double fluidity) {
	this.viscosity = fluidity;
    }
}
