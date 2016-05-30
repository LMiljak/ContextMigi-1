package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * A message class that contains a new health value for one of the
 * android device players.
 */
@Serializable
public class DamageMessage extends AbstractMessage {
    
    private int health;
    private PlatformPosition pos;
    
    public DamageMessage() {
        this.health = 3;
        this.pos = PlatformPosition.FRONTLEFT;
    }
    
    /**
     * Creates a DamageMessage and sets the health value.
     * @param health
     *              the health value that is used for updating the hearts
     *              in the android app.
     */
    public DamageMessage(int health, PlatformPosition pos) {
        this.health = health;
        this.pos = pos;
    }
    
    /**
     * Returns the health value of the message.
     * @return health int
     */
    public int getHealth(){
        return health;
    }
    
    /**
     * Returns the PlatformPosition of the message, used for identfication.
     * @return pos, PlatformPosition
     */
    public PlatformPosition getPos() {
        return pos;
    }
}
