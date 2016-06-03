package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class EnableSprayToVRMessage extends AbstractMessage {

    private PlatformPosition newSprayPosition;

    public EnableSprayToVRMessage() { }

    public EnableSprayToVRMessage(PlatformPosition newPos) {
        this.newSprayPosition = newPos;
    }

    public PlatformPosition getPosition() {
        return newSprayPosition;
    }

}
