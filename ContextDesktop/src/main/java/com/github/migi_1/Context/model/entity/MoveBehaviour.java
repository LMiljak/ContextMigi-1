package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

/**
 * class for handling the creation of a certain moving behaviour for an entity. 
 * @author Damian
 *
 */
public class MoveBehaviour {
    
    /**
     * Creates a certain movable behaviour for an entity.
     * @return Vector3f for the entity to move to.
     */
    public Vector3f getMoveVector() {
        return new Vector3f(0, 0, 0);
    }
    
}
