/*
 * RelationGraphFunction.java
 *
 * $Id: RelationGraphFunction.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

/**
 * This is the function adapter that get the relation value used in the relation
 * graphic panel.
 * 
 * @see AbstractGraphFunction
 * @author US00852
 * @version $Id: RelationGraphFunction.java,v 1.3 2007/08/18 08:29:55 marco Exp
 *          $
 * 
 */
public class RelationGraphFunction extends AbstractGraphFunction {

	/**
	 * @see org.mmarini.fluid.swing.AbstractGraphFunction#getValue(int, int)
	 */
	@Override
	public double getValue(int i, int j) {
		return getFluidHandler().getRelationValue(i, j);
	}

}
