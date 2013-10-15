/*
 * AbstractUniverseModifier.java
 *
 * $Id: AbstractUniverseModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 12/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The AbstractUniverseModifier is the template class for the universe
 * modifiers.
 * <p>
 * It has a CellModifier that will be applied to the modifier cells.
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: AbstractUniverseModifier.java,v 1.3 2007/08/18 08:29:54 marco
 *          Exp $
 */
public abstract class AbstractUniverseModifier implements UniverseModifier {
	private CellModifier cellModifier;

	/**
	 * 
	 */
	protected AbstractUniverseModifier() {
	}

	/**
	 * @param cellModifier
	 */
	protected AbstractUniverseModifier(CellModifier cellModifier) {
		this.cellModifier = cellModifier;
	}

	/**
	 * Returns the cell modifier.
	 * 
	 * @return the cellModifier
	 */
	public CellModifier getCellModifier() {
		return cellModifier;
	}

	/**
	 * Sets the cell modifier.
	 * 
	 * @param modifier
	 *            the cellModifier to set
	 */
	public void setCellModifier(CellModifier modifier) {
		this.cellModifier = modifier;
	}
}
