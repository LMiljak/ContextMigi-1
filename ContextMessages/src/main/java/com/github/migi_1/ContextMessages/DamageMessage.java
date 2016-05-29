package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;

/**
 * A message class that contains a new health value for one of the
 * android device players.
 */
public class DamageMessage extends AbstractMessage {
    
    private int health;
    
    /**
     * Creates a DamageMessage and sets the health value.
     * @param health
     *              the health value that is used for updating the hearts
     *              in the android app.
     */
    public DamageMessage(int health) {
        this.health = health;
    }
    
    public int getHealth(){
        return health;
    }
}
