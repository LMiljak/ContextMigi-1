package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class EnableSprayToAppMessage extends AbstractMessage {

    private PlatformPosition newSprayPosition;

    public EnableSprayToAppMessage() { }

    public EnableSprayToAppMessage(PlatformPosition newPos) {
        this.newSprayPosition = newPos;
    }

    public PlatformPosition getPosition() {
        return newSprayPosition;
    }

}
