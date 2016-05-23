package com.github.migi_1.Context.model;

/**
 * Interface for everything that is killable,
 * forces implementation of health, and being able to take damage.
 * @author Damian
 *
 */
public interface IKillable {
    
    /**
     * Returns the health of the entity.
     * @return
     */
    int getHealth();
    
    /**
     * Sets the health of the entity.
     * @param health
     */
    void setHealth(int health);
    
    /**
     * Reduces the entity's health by the given damage. 
     * @param damage
     * @return
     */
    void takeDamage(int damage);
    
}
