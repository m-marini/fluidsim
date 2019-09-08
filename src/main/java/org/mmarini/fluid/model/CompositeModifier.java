/*
 * CompositeModifier.java
 *
 * $Id: CompositeModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 08/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The CompositeModifier composes a list of universe modifiers as a single
 * modifier.
 *
 * @author marco.marini@mmarini.org
 * @version $Id: CompositeModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 */
public class CompositeModifier implements UniverseModifier {
	private final List<UniverseModifier> list;

	/**
	 *
	 */
	public CompositeModifier() {
		list = new ArrayList<UniverseModifier>();
	}

	/**
	 *
	 * @param list
	 */
	public CompositeModifier(final Collection<UniverseModifier> list) {
		this.list = new ArrayList<UniverseModifier>(list);
	}

	/**
	 *
	 */
	public CompositeModifier(final UniverseModifier... modifiers) {
		this();
		for (final UniverseModifier m : modifiers) {
			list.add(m);
		}
	}

	/**
	 * @return the list
	 */
	public List<UniverseModifier> getList() {
		return list;
	}

	/**
	 * @see org.mmarini.fluid.model.UniverseModifier#modify(org.mmarini.fluid.model.Universe)
	 */
	@Override
	public void modify(final Universe universe) {
		for (final UniverseModifier m : list) {
			m.modify(universe);
		}
	}
}
