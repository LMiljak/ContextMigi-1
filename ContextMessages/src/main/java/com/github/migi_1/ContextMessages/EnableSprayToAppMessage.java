package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Message that is send to the app when a certain screen
 * should enable the spray text.
 */
@Serializable
public class EnableSprayToAppMessage extends AbstractMessage {

    private PlatformPosition newSprayPosition;

    /**Public empty constructor used by the networking library. **/
    public EnableSprayToAppMessage() { }

    /**
     * Constructor of the message.
     * @param newPos the position of the screen that has to enable the spray.
     */
    public EnableSprayToAppMessage(PlatformPosition newPos) {
        this.newSprayPosition = newPos;
    }

    /**
     * Getter for the position from the message.
     * @return the position send in the message.
     */
    public PlatformPosition getPosition() {
        return newSprayPosition;
    }

}
