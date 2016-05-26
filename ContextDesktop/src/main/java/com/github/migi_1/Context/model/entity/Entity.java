package com.github.migi_1.Context.model.entity;

import com.jme3.scene.Spatial;

/**
 * Represents an object that can be placed in an Environment.
 *
 * @author Marcel
 *
 */
public abstract class Entity implements IMovable {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result
                + ((moveBehaviour == null) ? 0 : moveBehaviour.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entity other = (Entity) obj;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (moveBehaviour == null) {
            if (other.moveBehaviour != null)
                return false;
        } else if (!moveBehaviour.equals(other.moveBehaviour))
            return false;
        return true;
    }

    private Spatial model;
    /** Move behaviour of particular Entity. */
    private MoveBehaviour moveBehaviour;

    /**
     * Constructor for abstract Entity where the default behaviour
     * is a StaticMoveBehaviour.
     */
    public Entity() {
        moveBehaviour = new StaticMoveBehaviour();
        this.model = getDefaultModel();
    }

    /**
     * Gets the MoveBehaviour of the Entity.
     * @return MoveBehaviour
     */
    @Override
    public MoveBehaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    /**
     * Sets the MoveBehaviour of the Entity.
     * @param moveBehaviour MoveBehaviour
     */
    @Override
    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }

    /**
     * Gets the model of the Entity.
     */
    @Override
    public Spatial getModel() {
        return model;
    }

    /**
     * Sets the model of the Entity.
     */
    @Override
    public void setModel(Spatial model) {
        this.model = model;

    }

    /**
     * Get the default model for a particular instance.
     * @return defaultModel
     */
    public abstract Spatial getDefaultModel();
}
