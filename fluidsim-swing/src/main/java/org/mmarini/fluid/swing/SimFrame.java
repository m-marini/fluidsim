/*
 * SimFrame.java
 *
 * $Id: SimFrame.java,v 1.2 2007/08/12 08:36:00 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.MessageFormat;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main frame and the entry point of the fluid simulator.
 * <p>
 * It has a toolbar in the top, a main panel in the center and a info bar in the
 * bottom of frame.
 * </p>
 * 
 * @author US00852
 * @version $Id: SimFrame.java,v 1.2 2007/08/12 08:36:00 marco Exp $
 * 
 */
public class SimFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension INITIAL_SIZE = new Dimension(400, 300);

	private static Logger logger = LoggerFactory.getLogger(SimFrame.class);

	private JComponent mainPane;
	private JComponent toolBar;
	private JComponent info;

	/**
	 * 
	 */
	public SimFrame() {
	}

	/**
	 * Initialization method of the frame.
	 * <p>
	 * It creates the content of the frame, locates, sizes and shows the frame.
	 * </p>
	 * 
	 */
	protected void init() {
		logger.debug("init"); //$NON-NLS-1$
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(mainPane, BorderLayout.CENTER);
		if (info != null) {
			contentPane.add(info, BorderLayout.SOUTH);
		}

		String title = Messages.getString("SimFrame.title"); //$NON-NLS-1$
		title = MessageFormat
				.format(title,
						new Object[] {
								Messages.getString("SimFrame.name"), Messages.getString("SimFrame.version"), //$NON-NLS-1$ //$NON-NLS-2$
								Messages.getString("SimFrame.author") }); //$NON-NLS-1$
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(INITIAL_SIZE);
		setLocation((screen.width - INITIAL_SIZE.width) / 2,
				(screen.height - INITIAL_SIZE.height) / 2);
	}

	/**
	 * Sets the info panel
	 * 
	 * @param info
	 *            the info panel to set
	 */
	public void setInfo(JComponent info) {
		this.info = info;
	}

	/**
	 * Sets the main panel
	 * 
	 * @param mainPane
	 *            the mainPane to set
	 */
	public void setMainPane(JComponent graphPane) {
		this.mainPane = graphPane;
	}

	/**
	 * Sets the tool bar component.
	 * 
	 * @param toolBar
	 *            the toolBar to set
	 */
	public void setToolBar(JComponent toolBar) {
		this.toolBar = toolBar;
	}
}