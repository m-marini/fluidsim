/*
 * TabbedPane.java
 *
 * $Id: TabbedPane.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

/**
 * The TabbedPane is the main pane used in the simulator.
 * <p>
 * It has two component the cell component and the relations component.
 * <ul>
 * <li>the cell component shows the graphics of cells (pressure)</li>
 * <li>the relation component shows the graphics of relations (speed)</li>
 * <li>the flux component shows the graphics of flux (speed)</li>
 * </ul>
 * </p>
 * 
 * @author marco.marini@mmarini.org
 * @version $Id: TabbedPane.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 */
public class TabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	private JComponent cellComponent;

	private JComponent relationComponent;

	private JComponent fluxComponent;

	/**
	 * Returns the cell component
	 * 
	 * @return the cell component
	 */
	public JComponent getCellComponent() {
		return cellComponent;
	}

	/**
	 * @return the fluxComponent
	 */
	public JComponent getFluxComponent() {
		return fluxComponent;
	}

	/**
	 * Return the relation component
	 * 
	 * @return the relationComponent
	 */
	public JComponent getRelationComponent() {
		return relationComponent;
	}

	/**
	 * Adds the child components and sets the labels and the tool tips.
	 */
	public void init() {
		JComponent comp = getCellComponent();
		if (comp != null) {
			addTab(Messages.getString("TabbedPane.cellTab.text"), comp); //$NON-NLS-1$
			setToolTipTextAt(getComponentCount() - 1,
					Messages.getString("TabbedPane.cellTab.tips")); //$NON-NLS-1$
		}
		comp = getRelationComponent();
		if (comp != null) {
			addTab(Messages.getString("TabbedPane.relationTab.text"), comp); //$NON-NLS-1$
			setToolTipTextAt(getComponentCount() - 1,
					Messages.getString("TabbedPane.relationlTab.tips")); //$NON-NLS-1$
		}
		comp = getFluxComponent();
		if (comp != null) {
			addTab(Messages.getString("TabbedPane.fluxTab.text"), comp); //$NON-NLS-1$
			setToolTipTextAt(getComponentCount() - 1,
					Messages.getString("TabbedPane.fluxTab.tips")); //$NON-NLS-1$
		}
	}

	/**
	 * Sets the cell component.
	 * 
	 * @param cellComponent
	 *            the cellComponent to set
	 */
	public void setCellComponent(JComponent cellComponent) {
		this.cellComponent = cellComponent;
	}

	/**
	 * @param fluxComponent
	 *            the fluxComponent to set
	 */
	public void setFluxComponent(JComponent fluxComponent) {
		this.fluxComponent = fluxComponent;
	}

	/**
	 * Sets the relation component
	 * 
	 * @param relationComponent
	 *            the relationComponent to set
	 */
	public void setRelationComponent(JComponent relationComponent) {
		this.relationComponent = relationComponent;
	}
}
