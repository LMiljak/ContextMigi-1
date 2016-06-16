package com.github.migi_1.Context.model.entity.behaviour;

/**
 * MoveBehaviour class for all Entity objects.
 * @author Marcel
 *
 */
public abstract class EntityMoveBehaviour extends MoveBehaviour {

    private static final float ACCELERATION_FACTOR = 0.000001f;

    /**
     * Called when a collision has taken place.
     */
    public abstract void collided();

    /**
     * Returns the acceleration factor.
     * @return the acceleration factor.
     */
    public float getAcceleratingFactor() {
        return ACCELERATION_FACTOR;
    }
}
