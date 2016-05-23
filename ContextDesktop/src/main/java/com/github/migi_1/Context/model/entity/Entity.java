package com.github.migi_1.Context.model.entity;

/**
 * Superclass of all object that implement some MovableBehaviour.
 *
 * @author Marcel
 *
 */
public abstract class Entity implements IMovable{

    /**
     * Move behaviour of particular Entity.
     */
    private MoveBehaviour moveBehaviour;

    public Entity() {
        moveBehaviour = new StaticMoveBehaviour();
    }

    /**
     * Get the MoveBehaviour of the Entity.
     * @return MoveBehaviour
     */
    @Override
    public MoveBehaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    /**
     * Set the MoveBehaviour of the Entity.
     * @param moveBehaviour MoveBehaviour
     */
    @Override
    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }
}
