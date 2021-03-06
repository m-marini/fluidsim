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
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.mmarini.fluid.model.CellValueFunction;
import org.mmarini.fluid.model.FluidSimulator;
import org.mmarini.fluid.model.FluidSimulatorImpl;
import org.mmarini.fluid.model.FluxValueFunction;
import org.mmarini.fluid.model.RelationValueFunction;
import org.mmarini.fluid.model.Universe;
import org.mmarini.fluid.model.UniverseFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author us00852
 *
 */
public class Main {

	private static final String DEFAULT_MODIFIER_RESOURCE = "/wing.xml"; //$NON-NLS-1$
	public static final String DISABLED_ICON = "selectedIcon"; //$NON-NLS-1$
	private static final int INITIAL_WIDTH = 400;
	private static final int INITIAL_HEIGHT = 300;
	private static final double FLUX_OFFSET = 0.;
	private static final double FLUX_SCALE = 600e-3;
	private static final double SPEED_SCALE = 2000.;
	private static final double SPEED_OFFSET = 0.;
	private static final double CELL_OFFSET = 0.45;
	private static final double CELL_SCALE = 10.;
	private static final long RATE_INTERVAL = 300;
	private static final TimeUnit RATE_INTERVAL_UNIT = TimeUnit.MILLISECONDS;

	private static final UniverseFunction FluxFunc = new FluxValueFunction(FLUX_SCALE, FLUX_OFFSET);
	private static final UniverseFunction CellFunc = new CellValueFunction(CELL_SCALE, CELL_OFFSET);
	private static final UniverseFunction ReletionFunc = new RelationValueFunction(SPEED_SCALE, SPEED_OFFSET);

	private static Logger log = LoggerFactory.getLogger(Main.class);

	static double cellValue(final Universe universe, final int[] indexes) {
		return CellFunc.getValue(universe, indexes[0], indexes[1]);
	}

	static double fluxValue(final Universe universe, final int[] indexes) {
		return FluxFunc.getValue(universe, indexes[0], indexes[1]);
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new Main().start();
	}

	static double relationValue(final Universe universe, final int[] indexes) {
		return ReletionFunc.getValue(universe, indexes[0], indexes[1]);
	}

	private final JFrame frame;
	private final JToolBar toolBar;
	private final JMenuBar menuBar;
	private final GraphPane cellPane;
	private final GraphPane relationPane;
	private final GraphPane fluxPane;
	private final RateBar rateBar;
	private final Action newAction;
	private final Action openAction;
	private final Action exitAction;
	private final Action runAction;
	private final Action stepAction;
	private final Action stopAction;

	private final JFileChooser fileChooser;

//	private final FluidHandler fluidHandler;

	private final FluidSimulator fluidSimulator;

	/**
	 *
	 */
	public Main() {
		toolBar = new JToolBar();
		menuBar = new JMenuBar();
		rateBar = new RateBar();
		fileChooser = new JFileChooser();
		fluxPane = new GraphPane(Main::fluxValue);
		relationPane = new GraphPane(Main::relationValue);
		cellPane = new GraphPane(Main::cellValue);

		frame = new JFrame();

		newAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				createUniverse();
			}
		};
		openAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				open();
			}
		};
		exitAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				System.exit(0);
			}
		};
		runAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				startSimualtion();
			}
		};
		stepAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				stepSimulate();
			}

		};
		stopAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				stopSimualtion();
			}

		};
		fluidSimulator = new FluidSimulatorImpl();

		fluidSimulator.getRateFlow().sample(RATE_INTERVAL, RATE_INTERVAL_UNIT).subscribe(rateBar::onRate);
		fluidSimulator.getUniverseFlow().subscribe(fluxPane::onUniverse);
		fluidSimulator.getUniverseFlow().subscribe(cellPane::onUniverse);
		fluidSimulator.getUniverseFlow().subscribe(relationPane::onUniverse);
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	private ImageIcon createIcon(final String key) {
		final String value = Messages.getString(key);
		return !value.startsWith("!") ? new ImageIcon(getClass().getResource(value)) : null;
	}

	/**
	 *
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private void createModelBeans() throws ParserConfigurationException, SAXException, IOException {
		fluidSimulator.loadUniverseModifier(getClass().getResource(DEFAULT_MODIFIER_RESOURCE));
	}

	/**
	 *
	 *
	 * @return
	 */
	private JTabbedPane createTabPane() {
		final JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab(Messages.getString("Main.cellTab.text"), cellPane); //$NON-NLS-1$
		tabPane.setToolTipTextAt(0, Messages.getString("Main.cellTab.tips")); //$NON-NLS-1$
		tabPane.addTab(Messages.getString("Main.relationTab.text"), relationPane); //$NON-NLS-1$
		tabPane.setToolTipTextAt(1, Messages.getString("Main.relationlTab.tips")); //$NON-NLS-1$
		tabPane.addTab(Messages.getString("Main.fluxTab.text"), fluxPane); //$NON-NLS-1$
		tabPane.setToolTipTextAt(2, Messages.getString("Main.cellTab.tips")); //$NON-NLS-1$
		return tabPane;
	}

	/**
	 *
	 */
	private void createUIBeans() {
		fileChooser.setFileFilter(new FileNameExtensionFilter(Messages.getString("Main.fileType.text"), //$NON-NLS-1$
				"xml")); //$NON-NLS-1$

		fluxPane.init();
		relationPane.init();
		cellPane.init();

		initAction();
		initMenuBar();
		initToolBar();

		final Container contentPane = frame.getContentPane();
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
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
		frame.setLocation((screen.width - INITIAL_WIDTH) / 2, (screen.height - INITIAL_HEIGHT) / 2);
	}

	/**
	 * Creates the universe.
	 */
	private void createUniverse() {
		log.debug("createUniverse"); //$NON-NLS-1$
		fluidSimulator.createNew();
	}

	/**
	 * Initializes the action labels.
	 */
	private void initAction() {
		setupAction(newAction, "newAction"); //$NON-NLS-1$
		setupAction(openAction, "openAction"); //$NON-NLS-1$
		setupAction(exitAction, "exitAction"); //$NON-NLS-1$
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
		menu.add(new JMenuItem(openAction));
		menu.add(new JSeparator());
		menu.add(new JMenuItem(exitAction));
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
		toolBar.add(new JButton(openAction));
		toolBar.add(new JButton(stepAction));
		toolBar.add(new JButton(runAction));
		toolBar.add(new JButton(stopAction));
	}

	/**
	 *
	 */
	private void open() {
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			try {
				fluidSimulator.loadUniverseModifier(fileChooser.getSelectedFile().toURI().toURL());
				createUniverse();
			} catch (final Exception e) {
				log.error(e.getMessage(), e);
				showError(e);
			}
		}
	}

	/**
	 * Sets up an action loading the properties of the action.
	 *
	 * @param action the action to be set up
	 * @param name   the action name used in the localized resource file
	 */
	private void setupAction(final Action action, final String name) {
		action.putValue(Action.NAME, Messages.getString("Main." + name + ".name")); //$NON-NLS-1$ //$NON-NLS-2$
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(Messages.getString("Main." + name //$NON-NLS-1$
				+ ".accelerator"))); //$NON-NLS-1$
		action.putValue(Action.MNEMONIC_KEY, (int) Messages.getString("Main." + name + ".mnemonic") //$NON-NLS-1$ //$NON-NLS-2$
				.charAt(0));
		action.putValue(Action.SHORT_DESCRIPTION, Messages.getString("Main." + name + ".tip")); //$NON-NLS-1$ //$NON-NLS-2$

		final ImageIcon ic = createIcon("Main." + name + ".icon"); //$NON-NLS-1$ //$NON-NLS-2$
		if (ic != null) {
			action.putValue(Action.SMALL_ICON, ic);
		}
		final ImageIcon icd = createIcon("Main." + name + "." + DISABLED_ICON); //$NON-NLS-1$ //$NON-NLS-2$
		if (icd != null) {
			action.putValue(DISABLED_ICON, icd);
		}
	}

	/**
	 *
	 * @param e
	 */
	private void showError(final Exception e) {
		JOptionPane.showMessageDialog(frame, e.getLocalizedMessage(), Messages.getString("Main.error.title"), //$NON-NLS-1$
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 *
	 */
	private void start() {
		try {
			createModelBeans();
			createUIBeans();
			frame.setVisible(true);
		} catch (final Exception e) {
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
		fluidSimulator.single();
		SwingUtilities.invokeLater(this::tick);
	}

	/**
	 * Perform a single simulation step.
	 */
	private void stepSimulate() {
		fluidSimulator.single();
	}

	/**
	 * Stops the real time simulation.
	 */
	private void stopSimualtion() {
		log.debug("stopping simulation ..."); //$NON-NLS-1$
		fluidSimulator.stop();
		runAction.setEnabled(true);
		stepAction.setEnabled(true);
		stopAction.setEnabled(false);
	}

	private void tick() {
		if (stopAction.isEnabled()) {
			fluidSimulator.single();
			SwingUtilities.invokeLater(this::tick);
		}
	}
}
