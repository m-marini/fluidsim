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

import java.text.MessageFormat;

import javax.swing.JProgressBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.fluid.model.FluidHandler;

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
    private static Log log = LogFactory.getLog(RateBar.class);

    private static final long serialVersionUID = 1L;

    private FluidHandler fluidHandler;

    private long refreshTimeout;

    private long refreshPeriod;

    private double maxValue = 2;

    private double minValue = 1;

    /**
         * Initializes the bar.
         */
    public void init() {
    }

    /**
         * Refreshes the bar.
         */
    public void refresh() {
	long time = System.currentTimeMillis();
	if (time >= getRefreshTimeout()) {
	    refreshBar();
	    setRefreshTimeout(time + getRefreshPeriod());
	}
    }

    /**
         * Refresh and repaint the bar.
         */
    private void refreshBar() {
	double v = getFluidHandler().getSimulationRate();
	if (v > getMaxValue() || v < getMinValue()) {
	    scaleBar(v);
	}
	log.debug("Simulation Speed = " + v + " step/sec"); //$NON-NLS-1$ //$NON-NLS-2$
	int bar = (int) Math.round(getMaximum() * v / getMaxValue());
	setString(createMessage(v));
	setValue(bar);
    }

    /**
         * Creates the message to be shown in the bar.
         * <p>
         * The message show the value of rate in steps per second if greter then
         * one or in seconds per step if lower.
         * </p>
         * 
         * @param value
         *                the rate in steps per second
         * @return
         */
    private String createMessage(double value) {
	if (value >= 1) {
	    return MessageFormat
		    .format(
			    Messages.getString("RateBar.stepPerSec.message"), new Object[] { value }); //$NON-NLS-1$
	}
	return MessageFormat
		.format(
			Messages.getString("RateBar.secPerStep.message"), new Object[] { 1 / value }); //$NON-NLS-1$
    }

    /**
         * Calculates and sets the limits value of the rate.
         * 
         * @param v
         *                the value of rate.
         */
    private void scaleBar(double v) {
	double lv = Math.log10(v);
	double ls = Math.floor(lv);
	double scale = Math.pow(10, ls);
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
	setMinValue(min);
	setMaxValue(max);
	log.debug("Min = " + min); //$NON-NLS-1$ 
	log.debug("Max = " + max); //$NON-NLS-1$ 
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
         * Sets the fluid handler.
         * 
         * @param fluidHandler
         *                the fluidHandler to set
         */
    public void setFluidHandler(FluidHandler fluidHandler) {
	this.fluidHandler = fluidHandler;
    }

    /**
         * Returns the refresh timeout.
         * <p>
         * If the current time is greater then the timeout the rate sùhave to be
         * repainted.
         * </p>
         * 
         * @return the refreshTimeout
         */
    private long getRefreshTimeout() {
	return refreshTimeout;
    }

    /**
         * Sets the refresh timeout.
         * 
         * @param refreshTimeout
         *                the refreshTimeout to set
         */
    private void setRefreshTimeout(long lastRefresh) {
	this.refreshTimeout = lastRefresh;
    }

    /**
         * Returns the refresh period.
         * 
         * @return the refreshPeriod in msec.
         */
    private long getRefreshPeriod() {
	return refreshPeriod;
    }

    /**
         * Sets the refresh period.
         * <p>
         * The property determines how often the rate bar will be repoainted.
         * 
         * @param refreshPeriod
         *                the refreshPeriod to set in msec.
         */
    public void setRefreshPeriod(long refreshPeriod) {
	this.refreshPeriod = refreshPeriod;
    }

    /**
         * Returns the current max value of rate bar.
         * 
         * @return the maxValue
         */
    private double getMaxValue() {
	return maxValue;
    }

    /**
         * Sets the maximum value of the rate bar.
         * <p>
         * A value greater then the maximum determines a recalculation of the
         * rate scale.
         * </p>
         * 
         * @param maxValue
         *                the maxValue to set
         */
    private void setMaxValue(double maxValue) {
	this.maxValue = maxValue;
    }

    /**
         * Returns the minimum value of the rate bar.
         * 
         * @return the minValue
         */
    private double getMinValue() {
	return minValue;
    }

    /**
         * Sets the minimum value of the rate bar.
         * <p>
         * A value less then the minumum determines a recalulation of the bar
         * scale-
         * 
         * @param minValue
         *                the minValue to set
         */
    private void setMinValue(double minValue) {
	this.minValue = minValue;
    }
}