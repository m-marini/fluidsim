/*
 * FunctionModifier.java
 *
 * $Id: FunctionModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 13/ago/07
 *
 * Copyright notice
 */

package org.mmarini.fluid.model;

/**
 * The FunctionModifier is a cell modifier that changes the cell and/or relation
 * function.
 * <p>
 * It is used to set the material of the universe.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: FunctionModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 */
public class FunctionModifier implements CellModifier {
	private CellFunction cellFunction;
	private RelationFunction relationFunction;

	/**
	 *
	 */
	public FunctionModifier() {
	}

	/**
	 * @param cellFunction
	 * @param relationFunction
	 */
	public FunctionModifier(final CellFunction cellFunction, final RelationFunction relationFunction) {
		this.cellFunction = cellFunction;
		this.relationFunction = relationFunction;
	}

	/**
	 * Returns the cell function.
	 *
	 * @return the cellFunction
	 */
	public CellFunction getCellFunction() {
		return cellFunction;
	}

	/**
	 * Returns the relation function
	 *
	 * @return the relationFunction
	 */
	public RelationFunction getRelationFunction() {
		return relationFunction;
	}

	/**
	 * @see org.mmarini.fluid.model.CellModifier#modify(org.mmarini.fluid.model.Universe,
	 *      int, int)
	 */
	@Override
	public void modify(final Universe universe, final int i, final int j) {
		if (cellFunction != null) {
			universe.setCellFunction(i, j, cellFunction);
		}
		if (relationFunction != null) {
			universe.setRelationFunction(i, j, relationFunction);
		}
	}

	/**
	 * Sets the cell function.
	 *
	 * @param cellFunction the cellFunction to set or null if not present
	 */
	public void setCellFunction(final CellFunction function) {
		this.cellFunction = function;
	}

	/**
	 * Sets the relation function.
	 *
	 * @param relationFunction the relationFunction to set or null if not present
	 */
	public void setRelationFunction(final RelationFunction relationFunction) {
		this.relationFunction = relationFunction;
	}
}
