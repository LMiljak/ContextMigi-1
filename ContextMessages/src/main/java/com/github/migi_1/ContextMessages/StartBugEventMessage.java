package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Message send to the app when the bug event should start.
 */
@Serializable
public class StartBugEventMessage extends AbstractMessage {

    private PlatformPosition bugPosition;
    private PlatformPosition sprayPosition;

    /**
     * Empty constructor needed for the networking library.
     */
    public StartBugEventMessage() { }

    /**
     * The actual constructor used for messaging.
     * @param bugPosition the bug position in the event
     * @param sprayPosition the spray position in the event.
     */
    public StartBugEventMessage(PlatformPosition bugPos,
            PlatformPosition sprayPos) {
        bugPosition = bugPos;
        sprayPosition = sprayPos;
    };

    /**
     * Getter for the bug position.
     * @return the bug position.
     */
    public PlatformPosition getBugPosition() {
        return bugPosition;
    }

    /**
     * Getter from the spray position.
     * @return the spray position.
     */
    public PlatformPosition getSprayPosition() {
        return sprayPosition;
    }
}
