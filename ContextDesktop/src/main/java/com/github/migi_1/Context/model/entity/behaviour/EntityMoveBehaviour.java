package com.github.migi_1.Context.model.entity.behaviour;

/**
 * MoveBehaviour class for all Entity objects.
 * @author Marcel
 *
 */
public abstract class EntityMoveBehaviour extends MoveBehaviour {

    /**
     * Called when a collision has taken place.
     */
    public abstract void collided();
}
