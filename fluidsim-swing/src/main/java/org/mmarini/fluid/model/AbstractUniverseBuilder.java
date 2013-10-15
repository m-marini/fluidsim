/*
 * AbstractUniverseBuilder.java
 *
 * $Id: AbstractUniverseBuilder.java,v 1.4 2007/08/18 08:29:54 marco Exp $
 *
 * 03/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.awt.Dimension;

/**
 * The AbstractUniverseBuilder implements a universe builder.
 * <p>
 * It creates a new universe and applies the registered modifier.
 * </p>
 * 
 * @author marco
 * @version $Id: AbstractUniverseBuilder.java,v 1.4 2007/08/18 08:29:54 marco
 *          Exp $
 */
public abstract class AbstractUniverseBuilder implements UniverseBuilder {

	private UniverseModifier universeModifier;

	/**
	 * Builds a universe.
	 * 
	 * @return the built universe
	 */
	@Override
	public Universe build() {
		Universe universe = createUniverse();
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
	 * The create factory method template that creates the universe.
	 * 
	 * @return the created universe
	 */
	protected abstract Universe createUniverse();

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