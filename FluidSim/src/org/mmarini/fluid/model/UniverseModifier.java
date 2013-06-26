/*
 * UniverseModifier.java
 *
 * $Id: UniverseModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 *
 * 08/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

/**
 * The UniverseModifier is the interface of modifiers of universe.
 * <p>
 * The modifiers can change the universe in several manner.
 * </p>
 * 
 * @author US00852
 * @version $Id: UniverseModifier.java,v 1.3 2007/08/18 08:29:54 marco Exp $
 */
public interface UniverseModifier {
    /**
         * Modifies the universe.
         * 
         * @param universe
         *                the modifying universe
         */
    public abstract void modify(Universe universe);

}
