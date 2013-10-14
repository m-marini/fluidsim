/**
 * 
 */
package org.mmarini.fluid.model;

import java.awt.Dimension;

/**
 * @author us00852
 * 
 */
public class UniverseBuilderImpl extends AbstractUniverseBuilder {

	private TimeContext timeContext;
	private Dimension size;
	private CellUpdateContext cellContext;
	private RelationUpdateContext relationContext;

	/**
	 * 
	 */
	public UniverseBuilderImpl() {
		timeContext = new TimeContext();
		size = new Dimension(80, 80);
		cellContext = new CellUpdateContext();
		relationContext = new RelationUpdateContext();

		relationContext.setTimeContext(timeContext);
		cellContext.setTimeContext(timeContext);
	}

	/**
	 * @see org.mmarini.fluid.model.AbstractUniverseBuilder#createUniverse()
	 */
	@Override
	protected Universe createUniverse() {
		UniverseImpl universe = new UniverseImpl();

		universe.setSize(size);
		universe.setCellContext(cellContext);
		universe.setTimeContext(timeContext);
		universe.setRelationContext(relationContext);
		
		return universe;
	}

}
