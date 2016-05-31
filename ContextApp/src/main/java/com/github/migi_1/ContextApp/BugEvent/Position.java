/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.migi_1.ContextApp.BugEvent;

/**
 *
 * @author Nils
 */
public enum Position {
    FRONT_LEFT, FRONT_RIGHT, BACK_RIGHT, BACK_LEFT;
    
    private static Position[] positions = values();
    
    public Position getRight() {
        return positions[(this.ordinal()+1) % positions.length];
    }
    
    public Position getLeft() {
        int pos = this.ordinal() - 1;
        if(pos < 0) {
            pos += positions.length;
        }
        return positions[pos % positions.length];
    }
}

