package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class EnableSprayMessage extends AbstractMessage {

    private PlatformPosition newSprayPosition;

    public EnableSprayMessage() { }

    public EnableSprayMessage(PlatformPosition newPos) {
        this.newSprayPosition = newPos;
    }

    public PlatformPosition getPosition() {
        return newSprayPosition;
    }

}
