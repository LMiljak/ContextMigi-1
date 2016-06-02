/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;

/**
 *
 * @author Nils
 */
public class ChangeSprayPositionMessage extends AbstractMessage {
    
    private String oldSprayPosition;
    private String newSprayPosition;
    
    public ChangeSprayPositionMessage(String oldPos, String newPos) {
        this.oldSprayPosition = oldPos;
        this.newSprayPosition = newPos;
    }
    
    public String getOldPosition() {
        return oldSprayPosition;
    }
    
    public String getNewPosition() {
        return newSprayPosition;
    }
    
}
