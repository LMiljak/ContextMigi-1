package com.github.migi_1.Context.model.entity;

import com.jme3.scene.Spatial;

/**
 * Superclass of all object that implement some MovableBehaviour.
 *
 * @author Marcel
 *
 */
public abstract class Entity implements IMovable{

    private Spatial model;

    /**
     * Move behaviour of particular Entity.
     */
    private MoveBehaviour moveBehaviour;

    /**
     * Instantiate a generic MoveBehaviour.
     */
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

    /**
     * Get the model of the Entity.
     */
    @Override
    public Spatial getModel() {
        return model;
    }

    /**
     * Set the model of the Entity
     */
    @Override
    public void setModel(Spatial model) {
        this.model = model;

    }
}
