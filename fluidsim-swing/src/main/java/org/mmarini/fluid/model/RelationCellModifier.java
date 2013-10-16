/*
 * RelationCellModifier.java
 *
 * $Id: RelationCellModifier.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 *
 * 16/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The RelationCellModifier is a cell modifier that set up the relation function
 * in any given main directions.
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: RelationCellModifier.java,v 1.2 2007/08/18 08:29:54 marco Exp $
 * 
 */
public class RelationCellModifier implements CellModifier {
	private List<RelationFunction> list;

	/**
	 * 
	 */
	public RelationCellModifier() {
		list = new ArrayList<RelationFunction>();
	}

	/**
	 * 
	 */
	public RelationCellModifier(RelationFunction... functions) {
		this();
		for (RelationFunction f : functions) {
			list.add(f);
		}
	}

	/**
	 * Returns the list of relation functions.
	 * 
	 * @return the list
	 */
	public List<RelationFunction> getList() {
		return list;
	}

	/**
	 * @see org.mmarini.fluid.model.CellModifier#modify(org.mmarini.fluid.model.Universe,
	 *      int, int)
	 */
	@Override
	public void modify(Universe universe, int i, int j) {
		int direction = 0;
		for (Iterator<RelationFunction> iter = getList().iterator(); iter
				.hasNext(); ++direction) {
			RelationFunction rf = iter.next();
			if (rf != null) {
				universe.setRelationFunction(direction, i, j, rf);
			}
		}
	}

}
