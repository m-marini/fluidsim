/*
 * FluxGraphFunction.java
 *
 * $Id: FluxGraphFunction.java,v 1.2 2007/08/18 08:29:55 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

/**
 * This is the function adapter that get the flux value used in the flux
 * graphic panel.
 * 
 * @see AbstractGraphFunction
 * @author US00852
 * @version $Id: FluxGraphFunction.java,v 1.2 2007/08/18 08:29:55 marco Exp $
 * 
 */
public class FluxGraphFunction extends AbstractGraphFunction {

    /**
         * @see org.mmarini.fluid.swing.AbstractGraphFunction#getValue(int, int)
         */
    @Override
    public double getValue(int i, int j) {
	return getFluidHandler().getFluxValue(i, j);
    }

}
