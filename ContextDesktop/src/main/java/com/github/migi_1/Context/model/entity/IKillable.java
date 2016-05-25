package com.github.migi_1.Context.model.entity;

/**
 * Interface for everything object that can get killed.
 * Forces implementation of health and being able to take damage.
 * @author Damian
 *
 */
public interface IKillable {

    /**
     * Gets the amount of health of the entity.
     * @return the current health of the entity.
     */
    int getHealth();

    /**
     * Sets the amount of health of the entity.
     * @param health how much the entity's health should be.
     */
    void setHealth(int health);

    /**
     * Reduces the entity's health by the given damage.
     * Calls the onKilled method if the health reaches 0 or less
     * after this call.
     * @param damage how much damage to deal to the entity
     */
    default void takeDamage(int damage) {
    	setHealth(getHealth() - damage);
    	if (getHealth() <= 0) {
    		onKilled();
    	}
    }

    /**
     * This method is called when the object takes damage and as
     * a result of that gets 0 health or less.
     */
    void onKilled();

}
