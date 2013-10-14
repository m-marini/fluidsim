/*
 * UniverseBuilder.java
 *
 * $Id: UniverseBuilder.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * This is the interface of the universe builders.
 * 
 * @author marco
 * @version $Id: UniverseBuilder.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public interface UniverseBuilder {

	/**
	 * Builds the universe.
	 * 
	 * @return the built universe
	 */
	public abstract Universe build();

}