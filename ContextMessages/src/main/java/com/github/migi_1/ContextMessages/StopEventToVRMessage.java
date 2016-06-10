package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Message send to the VR when the bug event is finished by one of the players.
 * This message will trigger the sending of the "StopAllEventsMessage",
 * which stops the activity for all players.
 */
@Serializable
public class StopEventToVRMessage extends AbstractMessage {

    /**
     * Empty constructor, since no objects need to be send with this message.
     * The constructor is still needed for the networking library.
     */
    public StopEventToVRMessage() { }
}
