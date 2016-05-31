package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * A message class that contains the position of a player that is attacking
 * and the direction in which he is attacking in the form of a string.
 */
@Serializable
public class AttackMessage extends AbstractMessage {
    
    private PlatformPosition position;
    private String direction;
    
    /**
     * The constructor of AttackMessage without arguments.
     * Produces an AttackMessage with the default values.
     */
    public AttackMessage() {
        this.position = PlatformPosition.FRONTRIGHT;
        this.direction = "left";
    }
    
    /**
     * Creates an AttackMessage with a position and direction of attack.
     * @param position
     *              the position of the player who's attacking.
     * @param direction 
     *              the direction the player is attacking in.
     */
    public AttackMessage(PlatformPosition position, String direction) {
        this.position = position;
        if(direction == "middle" || direction == "right") {
            this.direction = direction;
        }
        else {
            this.direction = "left";
        }
    }
    
    /**
     * Returns the PlatformPosition of the attacking player.
     * @return position PlatformPosition
     */
    public PlatformPosition getPosition() {
        return position;
    }
    
    /**
     * Returns the direction the player is attacking in.
     * @return direction String
     */
    public String getDirection() {
        return direction;
    }
    
}
