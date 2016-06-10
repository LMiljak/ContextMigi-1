package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Message send to the app when the bug event has finished.
 */
@Serializable
public class StopAllEventsMessage extends AbstractMessage {

    /**
     * Empty constructor, since no objects need to be send with this message.
     * The constructor is still needed for the networking library.
     */
    public StopAllEventsMessage() { }

}
