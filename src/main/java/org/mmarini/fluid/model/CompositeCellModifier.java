/*
 * CompositeModifier.java
 *
 * $Id: CompositeCellModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 08/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * This is a composite cell modifier.
 * <p>
 * It mantains a list of cell modifiers and applies them in the list order.
 * </p>
 *
 * @author marco.marini@mmarini.org
 * @version $Id: CompositeCellModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp
 *          $
 *
 */
public class CompositeCellModifier implements CellModifier {
	private final List<CellModifier> list;

	/**
	 *
	 */
	public CompositeCellModifier() {
		list = new ArrayList<CellModifier>();
	}

	/**
	 *
	 * @param modifiers
	 */
	public CompositeCellModifier(final CellModifier... modifiers) {
		this();
		for (final CellModifier m : modifiers) {
			list.add(m);
		}
	}

	/**
	 *
	 * @param list
	 */
	public CompositeCellModifier(final Collection<CellModifier> list) {
		this.list = new ArrayList<CellModifier>(list);
	}

	/**
	 * Returns the list of modifiers
	 *
	 * @return the list
	 */
	public List<CellModifier> getList() {
		return list;
	}

	/**
	 * Modifies the cell applibg all the modifier in the list.
	 */
	@Override
	public void modify(final Universe universe, final int i, final int j) {
		for (final Iterator<CellModifier> iter = getList().iterator(); iter.hasNext();) {
			iter.next().modify(universe, i, j);
		}
	}
}
