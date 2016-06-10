package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Message send to the app when the bug event should start.
 */
@Serializable
public class StartBugEventMessage extends AbstractMessage {

    /**
     * Empty constructor, since no objects need to be send with this message.
     * The constructor is still needed for the networking library.
     */
    public StartBugEventMessage() { };

}
