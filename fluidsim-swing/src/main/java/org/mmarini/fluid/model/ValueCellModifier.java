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
	 * Returns the cell value to be set
	 * 
	 * @return the cellValue
	 */
	private double getCellValue() {
		return cellValue;
	}

	/**
	 * Returns the right relation value
	 * 
	 * @return the rightRelation
	 */
	private double getRightRelation() {
		return rightRelation;
	}

	/**
	 * Returns the upper left relation value.
	 * 
	 * @return the upLeftRelation
	 */
	private double getUpLeftRelation() {
		return upLeftRelation;
	}

	/**
	 * Returns the upper right relation value.
	 * 
	 * @return the upRightRelation
	 */
	private double getUpRightRelation() {
		return upRightRelation;
	}

	/**
	 * Modifies the cell setting contant values to the cell value and the
	 * relations values.
	 * 
	 * @see org.mmarini.fluid.model.CellModifier#modify(org.mmarini.fluid.model.Universe,
	 *      int, int)
	 */
	public void modify(Universe universe, int i, int j) {
		DoubleBufferedDouble function = universe.getCell(i, j);
		function.setNextValue(getCellValue());
		function.swap();
		function = universe.getRelation(FluidConstants.RIGHT, i, j);
		if (function != null) {
			function.setNextValue(getRightRelation());
			function.swap();
		}
		function = universe.getRelation(FluidConstants.UP_RIGHT, i, j);
		if (function != null) {
			function.setNextValue(getUpRightRelation());
			function.swap();
		}
		function = universe.getRelation(FluidConstants.UP_LEFT, i, j);
		if (function != null) {
			function.setNextValue(getUpLeftRelation());
			function.swap();
		}
	}

	/**
	 * Sets the cell value
	 * 
	 * @param cellValue
	 *            the cellValue to set
	 */
	public void setCellValue(double cellValue) {
		this.cellValue = cellValue;
	}

	/**
	 * Sets the right relation value
	 * 
	 * @param rightRelation
	 *            the rightRelation to set
	 */
	public void setRightRelation(double rightRelation) {
		this.rightRelation = rightRelation;
	}

	/**
	 * Sets the upper left relation value.
	 * 
	 * @param upLeftRelation
	 *            the upLeftRelation to set
	 */
	public void setUpLeftRelation(double upLeftRelation) {
		this.upLeftRelation = upLeftRelation;
	}

	/**
	 * Sets the upper left relation value.
	 * 
	 * @param upRightRelation
	 *            the upRightRelation to set
	 */
	public void setUpRightRelation(double upRightRelation) {
		this.upRightRelation = upRightRelation;
	}

}
