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
    
    /**
     * The constructor of DamageMessage without arguments.
     * Produces an DamageMessage with the default values.
     */
    public DamageMessage() {
        this.health = 3;
        this.pos = PlatformPosition.FRONTRIGHT;
    }
    
    /**
     * Creates a DamageMessage and sets the health value.
     * @param health
     *              the health value that is used for updating the hearts
     *              in the android app.
     * @param pos
     *              the position of the player the message is intended for.
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
