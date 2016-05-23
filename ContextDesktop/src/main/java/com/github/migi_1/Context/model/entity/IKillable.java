package com.github.migi_1.Context.model.entity;

/**
 * Interface for everything that is killable,
 * forces implementation of health, and being able to take damage.
 * @author Damian
 *
 */
public interface IKillable {

    /**
     * Returns the health of the entity.
     * @return the current health of the entity.
     */
    int getHealth();

    /**
     * Sets the health of the entity.
     * @param health how much the entity's health should be.
     */
    void setHealth(int health);

    /**
     * Reduces the entity's health by the given damage.
     * @param damage how much damage to deal to the entity
     *
     */
    void takeDamage(int damage);

    /**
     * This method is called when the object has zero health points.
     */
    void onKilled();

}
