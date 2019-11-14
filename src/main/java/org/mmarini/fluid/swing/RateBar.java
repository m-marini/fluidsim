/*
 * RateBar.java
 *
 * $Id: RateBar.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 *
 * 09/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing;

import static org.mmarini.fluid.swing.Messages.format;

import javax.swing.JProgressBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RateBar shows a bar indicating the speed of simulation.
 * <p>
 * The bar calculates automatically the scale of the bar to better fit the
 * values. The scale is multiplies of ten by 1, 2, 5.
 * </p>
 * <p>
 * The bar repainting is rated in fixed interval defined by the refreshPeriod
 * property.
 *
 * @author US00852
 * @version $Id: RateBar.java,v 1.3 2007/08/18 08:29:55 marco Exp $
 */
public class RateBar extends JProgressBar {
	private static Logger log = LoggerFactory.getLogger(RateBar.class);

	private static final long serialVersionUID = 1L;

	private double maxValue;
	private double minValue;

	/**
	 *
	 */
	public RateBar() {
		maxValue = 2;
		minValue = 1;
		setMaximum(200);
		setStringPainted(true);
	}

	/**
	 * Refresh and repaint the bar.
	 */
	public void onRate(final double rate) {
		if (rate > maxValue || rate < minValue) {
			scaleBar(rate);
		}
//		log.debug("Simulation Speed = {} step/sec", rate); //$NON-NLS-1$
		final int bar = (int) Math.round(getMaximum() * rate / maxValue);
		setString(format("RateBar.stepPerSec.message", rate >= 1 ? rate : 1 / rate));
		setValue(bar);
	}

	/**
	 * Calculates and sets the limits value of the rate.
	 *
	 * @param v the value of rate.
	 */
	private void scaleBar(final double v) {
		final double lv = Math.log10(v);
		final double ls = Math.floor(lv);
		final double scale = Math.pow(10, ls);
		double min = scale;
		double max = 2 * scale;
		if (v > max) {
			min = max;
			max = scale * 5;
		}
		if (v > max) {
			min = max;
			max = scale * 10;
		}
		minValue = min;
		maxValue = max;
		log.debug("Min = " + min); //$NON-NLS-1$
		log.debug("Max = " + max); //$NON-NLS-1$
	}
}