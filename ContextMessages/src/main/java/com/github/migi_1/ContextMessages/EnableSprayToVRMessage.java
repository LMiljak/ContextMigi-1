package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Message is send to the VR when the spray is
 * "swiped to another screen".
 * The new position of the spray is send through this message.
 */
@Serializable
public class EnableSprayToVRMessage extends AbstractMessage {

    private PlatformPosition newSprayPosition;

    /**Public empty constructor used by the networking library. **/
    public EnableSprayToVRMessage() { }

    /**
     * Constructor for the new message.
     * @param newPos the new position the spray should be enabled on.
     */
    public EnableSprayToVRMessage(PlatformPosition newPos) {
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
