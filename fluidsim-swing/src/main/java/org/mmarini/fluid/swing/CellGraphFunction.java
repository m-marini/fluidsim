/*
 * CellGraphFunction.java
 *
 * $Id: CellGraphFunction.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

/**
 * This is the function adapter that get the cell value used in the cell graphic
 * panel.
 * 
 * @see AbstractGraphFunction
 * @author US00852
 * @version $Id: CellGraphFunction.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 */
public class CellGraphFunction extends AbstractGraphFunction {

	/**
	 * @see org.mmarini.fluid.swing.AbstractGraphFunction#getValue(int, int)
	 */
	@Override
	public double getValue(int i, int j) {
		return getFluidHandler().getCellValue(i, j);
	}
}
