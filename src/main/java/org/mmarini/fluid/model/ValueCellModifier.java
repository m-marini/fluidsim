/*
 * ValueCellModifier.java
 *
 * $Id: ValueCellModifier.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 * 12/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * This cell modifier sets constant values to the cell value and the relation
 * values.
 *
 * @author marco.marini@mmarini.org
 * @version $Id: ValueCellModifier.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 */
public class ValueCellModifier implements CellModifier {
	private double cellValue;
	private double rightRelation;
	private double upRightRelation;
	private double upLeftRelation;

	/**
	 *
	 */
	public ValueCellModifier() {
	}

	/**
	 * @param cellValue
	 * @param rightRelation
	 * @param upRightRelation
	 * @param upLeftRelation
	 */
	public ValueCellModifier(final double cellValue, final double rightRelation, final double upRightRelation,
			final double upLeftRelation) {
		this.cellValue = cellValue;
		this.rightRelation = rightRelation;
		this.upRightRelation = upRightRelation;
		this.upLeftRelation = upLeftRelation;
	}

	/**
	 * @return the cellValue
	 */
	public double getCellValue() {
		return cellValue;
	}

	/**
	 * Modifies the cell setting contant values to the cell value and the relations
	 * values.
	 *
	 * @see org.mmarini.fluid.model.CellModifier#modify(org.mmarini.fluid.model.Universe,
	 *      int, int)
	 */
	@Override
	public void modify(final Universe universe, final int i, final int j) {
		DoubleBufferedDouble function = universe.getCell(i, j);
		function.setNextValue(cellValue);
		function.swap();
		function = universe.getRelation(FluidConstants.RIGHT, i, j);
		if (function != null) {
			function.setNextValue(rightRelation);
			function.swap();
		}
		function = universe.getRelation(FluidConstants.UP_RIGHT, i, j);
		if (function != null) {
			function.setNextValue(upRightRelation);
			function.swap();
		}
		function = universe.getRelation(FluidConstants.UP_LEFT, i, j);
		if (function != null) {
			function.setNextValue(upLeftRelation);
			function.swap();
		}
	}
}
