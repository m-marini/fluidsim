/*
 * Messages.java
 *
 * $Id: Messages.java,v 1.2 2007/08/18 08:29:55 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.swing1;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The class is used to load the localized resources.
 * 
 * @author marco
 * @version $Id: Messages.java,v 1.2 2007/08/18 08:29:55 marco Exp $
 */
public class Messages {
	/**
	 * 
	 * @param key
	 * @param parms
	 * @return
	 */
	public static String format(final String key, final Object... parms) {
		return MessageFormat.format(Messages.getString(key), parms);
	}

	/**
	 * Returns the localized message.
	 * 
	 * @param key
	 *            the message key
	 * @return the localized message.
	 */
	public static String getString(final String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	private static final String BUNDLE_NAME = "org.mmarini.fluid.swing.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * 
	 */
	private Messages() {
	}
}
