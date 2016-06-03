/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Nils
 */
@Serializable
public class ChangeSprayPositionMessage extends AbstractMessage {

    private PlatformPosition newSprayPosition;

    private ChangeSprayPositionMessage() {

    }

    public ChangeSprayPositionMessage(PlatformPosition newPos) {
        this.newSprayPosition = newPos;
    }

    public PlatformPosition getNewPosition() {
        return newSprayPosition;
    }

}