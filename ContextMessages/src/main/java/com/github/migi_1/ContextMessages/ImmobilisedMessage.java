package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Messaged that tells whether a carrier becomes immobilised or stops being immobilised.
 * @author Marcel
 *
 */
@Serializable
public class ImmobilisedMessage extends AbstractMessage {

    private boolean immobilised;

    private PlatformPosition position;

    /**
     * Public empty constructor used by the JME3 networking
     * library.
     */
    public ImmobilisedMessage() { };

    /**
     * Creates an ImmobilisedMessage.
     * @param immobilised
     *                      True when the carrier becomes immobilised
     *                      False when the carrier stops being immobilised
     * @param position      Carrier position
     */
    public ImmobilisedMessage(boolean immobilised, PlatformPosition position) {
        this.immobilised = immobilised;
        this.position = position;
    }

    /**
     * Getter for the immobilised boolean.
     * @return true when becoming immobilised
     */
    public boolean getImmobilised() {
        return immobilised;
    }

    /**
     * Getter for the platform position.
     * @return platform position
     */
    public PlatformPosition getPosition() {
        return position;
    }
}
