package com.github.migi_1.Context.model.entity;

/**
 * Superclass of all object that implement some MovableBehaviour.
 *
 * @author Marcel
 *
 */
public abstract class Entity {

    /**
     * Move behaviour of particular Entity.
     */
    protected MoveBehaviour moveBehaviour;

    /**
     * Get the MoveBehaviour of the Entity.
     * @return MoveBehaviour
     */
    public MoveBehaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    /**
     * Set the MoveBehaviour of the Entity.
     * @param moveBehaviour MoveBehaviour
     */
    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }
}
