/*
 * AbstractUpdaterContext.java
 *
 * $Id: AbstractUpdaterContext.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 03/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The AbstractUpdaterContext is the template class for the update contexts.
 * <p>
 * It mantains the time context, the subject and the adjacent values.
 * </p>
 * 
 * @author marco
 * @version $Id: AbstractUpdaterContext.java,v 1.3 2007/08/18 08:29:54 marco Exp
 *          $
 */
public abstract class AbstractUpdaterContext {
	private DoubleBufferedDouble subject;

	private DoubleBufferedDouble[] adjacents;

	private TimeContext timeContext;

	/**
	 * Returns the adjacent values.
	 * 
	 * @return the adjacent values
	 */
	public DoubleBufferedDouble[] getAdjacents() {
		return adjacents;
	}

	/**
	 * Returns the subject.
	 * 
	 * @return the subject
	 */
	public DoubleBufferedDouble getSubject() {
		return subject;
	}

	/**
	 * Returns the time context.
	 * 
	 * @return the timeContext
	 */
	public TimeContext getTimeContext() {
		return timeContext;
	}

	/**
	 * Sets the adjacent values
	 * 
	 * @param adjacents
	 *            the adjacents to set
	 */
	public void setAdjacents(final DoubleBufferedDouble[] relative) {
		this.adjacents = relative;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(final DoubleBufferedDouble subject) {
		this.subject = subject;
	}

	/**
	 * Sets the time context.
	 * 
	 * @param timeContext
	 *            the timeContext to set
	 */
	public void setTimeContext(final TimeContext timeContext) {
		this.timeContext = timeContext;
	}
}
