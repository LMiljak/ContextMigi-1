package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

/**
 * class for handling the creation of a certain moving behaviour for an entity.
 * @author Damian
 *
 */
public abstract class MoveBehaviour {

    /**
     * Creates a certain movable behaviour for an entity.
     * @return Vector3f for the entity to move to.
     */
    public abstract Vector3f getMoveVector();

}
