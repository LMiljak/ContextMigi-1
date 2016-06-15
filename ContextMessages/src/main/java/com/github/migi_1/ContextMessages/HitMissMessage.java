package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * A message class that contains whether or not a carrier hit an enemy with their
 * attack.
 */
@Serializable
public class HitMissMessage extends AbstractMessage {
    
    private boolean hit;
    private PlatformPosition pos;
    
    /**
	 * Public empty constructor used by the JME3 networking
	 * library.
	 */
    public HitMissMessage() { }
    
    /**
     * Creates a HitMissMessage and sets the health value.
     * @param hit
     *              a boolean that represents whether or not you hit the enemy.
     * @param pos
     *              the position of the player the message is intended for.
     */
    public HitMissMessage(boolean hit, PlatformPosition pos) {
        this.hit = hit;
        this.pos = pos;
    }
    
    /**
     * Returns the hit boolean of the message.
     * @return whether or not the player has hit the enemy with their attack.
     */
    public boolean getHit() {
        return hit;
    }
    
    /**
     * Returns the PlatformPosition of the message, used for identfication.
     * @return the position of the player who attacked.
     */
    public PlatformPosition getPos() {
        return pos;
    }
}
