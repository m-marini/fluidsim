/**
 * 
 */
package org.mmarini.fluid.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import org.mmarini.fluid.model.FluidHandler;
import org.mmarini.fluid.model.FluidHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author us00852
 * 
 */
public class Main {

	private static final String DEFAULT_MODIFIER_RESOURCE = "/wing.xml";
	public static final String DISABLED_ICON = "selectedIcon"; //$NON-NLS-1$
	private static final int INITIAL_WIDTH = 400;
	private static final int INITIAL_HEIGHT = 300;

	private static Logger log = LoggerFactory.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main().start();
	}

	private JFrame frame;
	private JToolBar toolBar;
	private JMenuBar menuBar;
	private GraphPane cellPane;
	private GraphPane relationPane;
	private GraphPane fluxPane;
	private RateBar rateBar;
	private Action newAction;
	private Action closeAction;
	private Action runAction;
	private Action stepAction;
	private Action stopAction;
	private Runnable ticker;
	private FluidHandler fluidHandler;

	/**
	 * 
	 */
	public Main() {
		toolBar = new JToolBar();
		menuBar = new JMenuBar();
		rateBar = new RateBar();
		fluxPane = new GraphPane(new GraphFunction() {

			@Override
			public Dimension getSize() {
				return fluidHandler.getSize();
			}

			@Override
			public double getValue(int i, int j) {
				return fluidHandler.getFluxValue(i, j);
			}
		});
		relationPane = new GraphPane(new GraphFunction() {

			@Override
			public Dimension getSize() {
				return fluidHandler.getSize();
			}

			@Override
			public double getValue(int i, int j) {
				return fluidHandler.getRelationValue(i, j);
			}
		});
		cellPane = new GraphPane(new GraphFunction() {

			@Override
			public Dimension getSize() {
				return fluidHandler.getSize();
			}

			@Override
			public double getValue(int i, int j) {
				return fluidHandler.getCellValue(i, j);
			}
		});
		frame = new JFrame();

		newAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createUniverse();
			}
		};
		closeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		};
		runAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startSimualtion();
			}
		};
		stepAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stepSimulate();
			}

		};
		stopAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopSimualtion();
			}

		};

		ticker = new Runnable() {

			@Override
			public void run() {
				tick();
			}

		};
		fluidHandler = new FluidHandlerImpl();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	private ImageIcon createIcon(String key) {
		String value = Messages.getString(key);
		return !value.startsWith("!") ? new ImageIcon(getClass().getResource(
				value)) : null;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private void createModelBeans() throws ParserConfigurationException,
			SAXException, IOException {
		fluidHandler.loadUniverseModifier(getClass().getResource(
				DEFAULT_MODIFIER_RESOURCE));
	}

	/**
	 * 
	 * 
	 * @return
	 */
	private JTabbedPane createTabPane() {
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab(Messages.getString("Main.cellTab.text"), cellPane); //$NON-NLS-1$
		tabPane.setToolTipTextAt(0, Messages.getString("Main.cellTab.tips")); //$NON-NLS-1$
		tabPane.addTab(
				Messages.getString("Main.relationTab.text"), relationPane); //$NON-NLS-1$
		tabPane.setToolTipTextAt(1,
				Messages.getString("Main.relationlTab.tips")); //$NON-NLS-1$
		tabPane.addTab(Messages.getString("Main.fluxTab.text"), fluxPane); //$NON-NLS-1$
		tabPane.setToolTipTextAt(2, Messages.getString("Main.fluxTab.tips")); //$NON-NLS-1$
		return tabPane;
	}

	/**
	 * 
	 */
	private void createUIBeans() {
		rateBar.setFluidHandler(fluidHandler);

		fluxPane.init();
		relationPane.init();
		cellPane.init();

		initAction();
		initMenuBar();
		initToolBar();

		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(createTabPane(), BorderLayout.CENTER);
		contentPane.add(rateBar, BorderLayout.SOUTH);

		frame.setJMenuBar(menuBar);
		frame.setTitle(Messages.getString("Main.title")); //$NON-NLS-1$ 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Set size and center the frame in the screen
		 */
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
		frame.setLocation((screen.width - INITIAL_WIDTH) / 2,
				(screen.height - INITIAL_HEIGHT) / 2);
	}

	/**
	 * Creates the universe.
	 */
	private void createUniverse() {
		log.debug("createUniverse"); //$NON-NLS-1$
		fluidHandler.createNew();
	}

	/**
	 * Initializes the action labels.
	 */
	private void initAction() {
		setupAction(newAction, "newAction"); //$NON-NLS-1$
		setupAction(closeAction, "closeAction"); //$NON-NLS-1$
		setupAction(runAction, "runAction"); //$NON-NLS-1$
		setupAction(stopAction, "stopAction"); //$NON-NLS-1$
		setupAction(stepAction, "stepAction"); //$NON-NLS-1$
		stepAction.setEnabled(false);
	}

	/**
	 * Initializes the menu bar.
	 */
	private void initMenuBar() {
		JMenu menu = new JMenu(Messages.getString("Main.fileMenu.text")); //$NON-NLS-1$
		menu.add(new JMenuItem(newAction));
		menu.add(new JSeparator());
		menu.add(new JMenuItem(closeAction));
		menuBar.add(menu);

		menu = new JMenu(Messages.getString("Main.simulateMenu.text")); //$NON-NLS-1$
		menu.add(new JMenuItem(stepAction));
		menu.add(new JSeparator());
		menu.add(new JMenuItem(runAction));
		menu.add(new JMenuItem(stopAction));
		menuBar.add(menu);
	}

	/**
	 * Initializes the tool bar.
	 */
	private void initToolBar() {
		toolBar.add(new JButton(newAction));
		toolBar.add(new JButton(stepAction));
		toolBar.add(new JButton(runAction));
		toolBar.add(new JButton(stopAction));
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
		action.putValue(Action.NAME,
				Messages.getString("Main." + name + ".name"));
		action.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(Messages.getString("Main." + name
						+ ".accelerator")));
		action.putValue(Action.MNEMONIC_KEY,
				(int) Messages.getString("Main." + name + ".mnemonic")
						.charAt(0));
		action.putValue(Action.SHORT_DESCRIPTION,
				Messages.getString("Main." + name + ".tip"));

		ImageIcon ic = createIcon("Main." + name + ".icon"); //$NON-NLS-1$ //$NON-NLS-2$
		if (ic != null) {
			action.putValue(Action.SMALL_ICON, ic);
		}
		ImageIcon icd = createIcon("Main." + name + "." + DISABLED_ICON); //$NON-NLS-1$ //$NON-NLS-2$
		if (icd != null) {
			action.putValue(DISABLED_ICON, icd);
		}
	}

	/**
	 * 
	 */
	private void start() {
		try {
			createModelBeans();
			createUIBeans();
			frame.setVisible(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Starts the real time simulation.
	 */
	private void startSimualtion() {
		log.debug("starting simulation ..."); //$NON-NLS-1$
		runAction.setEnabled(false);
		stepAction.setEnabled(false);
		stopAction.setEnabled(true);
		fluidHandler.startSimulation();
		SwingUtilities.invokeLater(ticker);
	}

	/**
	 * Perform a single simulation step.
	 */
	private void stepSimulate() {
		fluidHandler.singleStepSimulation();
		cellPane.repaint();
		relationPane.repaint();
		fluxPane.repaint();
	}

	/**
	 * Stops the real time simulation.
	 */
	private void stopSimualtion() {
		log.debug("stopping simulation ..."); //$NON-NLS-1$
		runAction.setEnabled(true);
		stepAction.setEnabled(true);
		stopAction.setEnabled(false);
	}

	/**
	 * Perform a simulation step during the real time simulation.
	 */
	private void tick() {
		if (!runAction.isEnabled()) {
			fluidHandler.simulate();
			cellPane.repaint();
			relationPane.repaint();
			fluxPane.repaint();
			rateBar.refresh();
			SwingUtilities.invokeLater(ticker);
		}
	}
}
