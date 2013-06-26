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

import java.util.Iterator;
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
    private List<UniverseModifier> list;

    /**
         * @see org.mmarini.fluid.model.UniverseModifier#modify(org.mmarini.fluid.model.Universe)
         */
    public void modify(Universe universe) {
	for (Iterator<UniverseModifier> iter = getList().iterator(); iter
		.hasNext();) {
	    iter.next().modify(universe);
	}
    }

    /**
         * Returns the list of universe modifiers.
         * 
         * @return the list
         */
    public List<UniverseModifier> getList() {
	return list;
    }

    /**
         * Sets the list of universe modifiers.
         * 
         * @param list
         *                the list to set
         */
    public void setList(List<UniverseModifier> list) {
	this.list = list;
    }
}
