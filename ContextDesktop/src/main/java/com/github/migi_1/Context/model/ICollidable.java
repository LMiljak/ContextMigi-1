package com.github.migi_1.Context.model;

import com.jme3.collision.CollisionResults;
/**
 * Interface for all collidable objects.
 * @author Marcel
 *
 */
public interface ICollidable extends IDisplayable {

    /**
     * Handle collisions with another ICollidable.
     * @param arg0 Collidable for which collision must be checked.
     * @param arg1 CollisionResults instance
     */
    void collideWith(ICollidable arg0, CollisionResults arg1);
}
