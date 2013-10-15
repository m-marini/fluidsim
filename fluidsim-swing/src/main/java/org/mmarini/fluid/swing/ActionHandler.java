/*
 * ActionHandler.java
 *
 * $Id: ActionHandler.java,v 1.4 2007/08/18 08:29:55 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.mmarini.fluid.model.FluidHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ActionHandler manages the interaction with the user.
 * <p>
 * The handled action are:
 * <ul>
 * <li>close simulation application</li>
 * <li>create a new universe</li>
 * <li>run the simulation</li>
 * <li>stop the simulation</li>
 * <li>run a single step simulation</li>
 * </ul>
 * </p>
 * <p>
 * It also creates the menu bar and the tool bar.
 * </p>
 * 
 * @author US00852
 * @version $Id: ActionHandler.java,v 1.4 2007/08/18 08:29:55 marco Exp $
 */
public class ActionHandler {
	public static final String DISABLED_ICON = "selectedIcon"; //$NON-NLS-1$

	private static Logger log = LoggerFactory.getLogger(ActionHandler.class);

	private JToolBar toolBar;
	private JMenuBar menuBar;
	private GraphPane cellPane;
	private GraphPane relationPane;
	private GraphPane fluxPane;
	private FluidHandler fluidHandler;
	private RateBar rateBar;

	private Action newAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			createUniverse();
		}
	};

	private Action closeAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	};

	private Action runAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			startSimualtion();
		}
	};

	private Action stepAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			stepSimulate();
		}

	};

	private Action stopAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			stopSimualtion();
		}

	};

	private Runnable ticker = new Runnable() {

		@Override
		public void run() {
			tick();
		}

	};

	/**
	 * Creates the universe.
	 */
	private void createUniverse() {
		log.debug("createUniverse"); //$NON-NLS-1$
		getFluidHandler().createNew();
	}

	/**
	 * Returns the cell graphic panel.
	 * 
	 * @return the cellPane
	 */
	private GraphPane getCellPane() {
		return cellPane;
	}

	/**
	 * Returns the close action.
	 * 
	 * @return the closeAction
	 */
	private Action getCloseAction() {
		return closeAction;
	}

	/**
	 * Returns the fluid handler.
	 * 
	 * @return the fluidHandler
	 */
	private FluidHandler getFluidHandler() {
		return fluidHandler;
	}

	/**
	 * Returns the flux panel.
	 * 
	 * @return the fluxPane
	 */
	private GraphPane getFluxPane() {
		return fluxPane;
	}

	/**
	 * Returns the menu bar
	 * 
	 * @return the menuBar
	 */
	private JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * Returns the new action.
	 * 
	 * @return the newAction
	 */
	private Action getNewAction() {
		return newAction;
	}

	/**
	 * Returns the rate bar.
	 * 
	 * @return the rateBar
	 */
	private RateBar getRateBar() {
		return rateBar;
	}

	/**
	 * Returns the relation graphic panel.
	 * 
	 * @return the relationPane
	 */
	private GraphPane getRelationPane() {
		return relationPane;
	}

	/**
	 * @return the runAction
	 */
	private Action getRunAction() {
		return runAction;
	}

	/**
	 * Returns the step action.
	 * 
	 * @return the stepAction
	 */
	private Action getStepAction() {
		return stepAction;
	}

	/**
	 * Returns the stop action.
	 * 
	 * @return the stopAction
	 */
	private Action getStopAction() {
		return stopAction;
	}

	/**
	 * Returns the ticker used during the real time simulation.
	 * 
	 * @return the ticker
	 */
	private Runnable getTicker() {
		return ticker;
	}

	/**
	 * Returns the tool bar.
	 * 
	 * @return the toolBar
	 */
	private JToolBar getToolBar() {
		return toolBar;
	}

	/**
	 * Initializes the action handler.
	 * <p>
	 * It initializes the action labels, the menu bar and the tool bar.
	 * 
	 */
	public void init() {
		initAction();
		initMenuBar();
		initToolBar();
	}

	/**
	 * Initializes the action labels.
	 */
	private void initAction() {
		setupAction(getNewAction(), "newAction"); //$NON-NLS-1$
		setupAction(getCloseAction(), "closeAction"); //$NON-NLS-1$
		setupAction(getRunAction(), "runAction"); //$NON-NLS-1$
		setupAction(getStopAction(), "stopAction"); //$NON-NLS-1$
		setupAction(getStepAction(), "stepAction"); //$NON-NLS-1$
		getStopAction().setEnabled(false);
	}

	/**
	 * Initializes the menu bar.
	 */
	private void initMenuBar() {
		JMenuBar menuBar = getMenuBar();
		JMenu menu = new JMenu(
				Messages.getString("ActionHandler.fileMenu.text")); //$NON-NLS-1$
		JMenuItem item = new JMenuItem();
		item.setAction(getNewAction());
		menu.add(item);

		menu.add(new JSeparator());

		item = new JMenuItem();
		item.setAction(getCloseAction());
		menu.add(item);

		menuBar.add(menu);

		menu = new JMenu(Messages.getString("ActionHandler.simulateMenu.text")); //$NON-NLS-1$
		item = new JMenuItem();
		item.setAction(getStepAction());
		menu.add(item);

		menu.add(new JSeparator());

		item = new JMenuItem();
		item.setAction(getRunAction());
		menu.add(item);

		item = new JMenuItem();
		item.setAction(getStopAction());
		menu.add(item);
		menuBar.add(menu);
	}

	/**
	 * Initializes the tool bar.
	 */
	private void initToolBar() {
		JToolBar tb = getToolBar();
		JButton btn = new JButton();
		btn.setAction(getNewAction());
		tb.add(btn);
		btn = new JButton();
		btn.setAction(getStepAction());
		tb.add(btn);
		btn = new JButton();
		btn.setAction(getRunAction());
		tb.add(btn);
		btn = new JButton();
		btn.setAction(getStopAction());
		tb.add(btn);
	}

	/**
	 * Sets the cell graphic panel.
	 * 
	 * @param cellPane
	 *            the cellPane to set
	 */
	public void setCellPane(GraphPane graphPane) {
		this.cellPane = graphPane;
	}

	/**
	 * Sets the fluid handler.
	 * 
	 * @param fluidHandler
	 *            the fluidHandler to set
	 */
	public void setFluidHandler(FluidHandler fluidHandler) {
		this.fluidHandler = fluidHandler;
	}

	/**
	 * Sets the flux panel.
	 * 
	 * @param fluxPane
	 *            the fluxPane to set
	 */
	public void setFluxPane(GraphPane fluxPane) {
		this.fluxPane = fluxPane;
	}

	/**
	 * Sets the menu bar.
	 * 
	 * @param menuBar
	 *            the menuBar to set
	 */
	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	/**
	 * Sets rate bar.
	 * 
	 * @param rateBar
	 *            the rateBar to set
	 */
	public void setRateBar(RateBar rateBar) {
		this.rateBar = rateBar;
	}

	/**
	 * Sets the relation graphic panel.
	 * 
	 * @param relationPane
	 *            the relationPane to set
	 */
	public void setRelationPane(GraphPane relationPane) {
		this.relationPane = relationPane;
	}

	/**
	 * Sets the tool bar.
	 * 
	 * @param toolBar
	 *            the toolBar to set
	 */
	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	/**
	 * Sets up an action loading the properties of the action.
	 * 
	 * @param action
	 *            the action to be set up
	 * @param name
	 *            the action name used in the localized resource file
	 */
	private void setupAction(Action action, String name) {

		String value = Messages.getString("ActionHandler." + name + ".name"); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.NAME, value);

		value = Messages.getString("ActionHandler." + name + ".accelerator"); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(value));

		value = Messages.getString("ActionHandler." + name + ".mnemonic"); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.MNEMONIC_KEY, Integer.valueOf(value.charAt(0)));

		value = Messages.getString("ActionHandler." + name + ".tip"); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.SHORT_DESCRIPTION, value);

		value = Messages.getString("ActionHandler." + name + ".icon"); //$NON-NLS-1$ //$NON-NLS-2$
		if (!value.startsWith("!")) { //$NON-NLS-1$
			URL url = getClass().getResource(value);
			if (url != null)
				action.putValue(Action.SMALL_ICON, new ImageIcon(url));
		}
		value = Messages
				.getString("ActionHandler." + name + "." + DISABLED_ICON); //$NON-NLS-1$ //$NON-NLS-2$
		if (!value.startsWith("!")) { //$NON-NLS-1$
			URL url = getClass().getResource(value);
			if (url != null)
				action.putValue(DISABLED_ICON, new ImageIcon(url));
		}
	}

	/**
	 * Starts the real time simulation.
	 */
	private void startSimualtion() {
		log.debug("starting simulation ..."); //$NON-NLS-1$
		getRunAction().setEnabled(false);
		getStepAction().setEnabled(false);
		getStopAction().setEnabled(true);
		getFluidHandler().startSimulation();
		SwingUtilities.invokeLater(getTicker());
	}

	/**
	 * Perform a single simulation step.
	 */
	private void stepSimulate() {
		getFluidHandler().singleStepSimulation();
		getCellPane().repaint();
		getRelationPane().repaint();
		getFluxPane().repaint();
	}

	/**
	 * Stops the real time simulation.
	 */
	private void stopSimualtion() {
		log.debug("stopping simulation ..."); //$NON-NLS-1$
		getRunAction().setEnabled(true);
		getStepAction().setEnabled(true);
		getStopAction().setEnabled(false);
	}

	/**
	 * Perform a simulation step during the real time simulation.
	 */
	private void tick() {
		if (!getRunAction().isEnabled()) {
			getFluidHandler().simulate();
			getCellPane().repaint();
			getRelationPane().repaint();
			getFluxPane().repaint();
			getRateBar().refresh();
			SwingUtilities.invokeLater(getTicker());
		}
	}
}