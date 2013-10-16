/**
 * 
 */
package org.mmarini.fluid.model;

import java.awt.Dimension;

/**
 * @author us00852
 * 
 */
public class UniverseBuilderImpl {
	private TimeContext timeContext;
	private Dimension size;
	private CellUpdateContext cellContext;
	private RelationUpdateContext relationContext;
	private UniverseModifier universeModifier;

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
	 * Builds a universe.
	 * 
	 * @return the built universe
	 */
	public Universe build() {
		UniverseImpl universe = new UniverseImpl();

		universe.setSize(size);
		universe.setCellContext(cellContext);
		universe.setTimeContext(timeContext);
		universe.setRelationContext(relationContext);

		if (universeModifier != null)
			universeModifier.modify(universe);
		Dimension size = universe.getSize();
		int w = size.width;
		int h = size.height;
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
				DoubleBufferedDouble function = universe.getCell(i, j);
				function.swap();
				function = universe.getRelation(FluidConstants.RIGHT, i, j);
				if (function != null)
					function.swap();
				function = universe.getRelation(FluidConstants.UP_RIGHT, i, j);
				if (function != null)
					function.swap();
				function = universe.getRelation(FluidConstants.UP_LEFT, i, j);
				if (function != null)
					function.swap();
			}
		}
		return universe;
	}

	/**
	 * Sets the universe modifier.
	 * 
	 * @param universeModifier
	 *            the universeModifier to set
	 */
	public void setUniverseModifier(UniverseModifier universeModifier) {
		this.universeModifier = universeModifier;
	}
}
