package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

/**
 * Abstract behaviour for objects that can move in the Environment.
 * @author Damian
 *
 */
public abstract class MoveBehaviour {

    /**
     * Gets the move vector of this behaviour.
     * This method is called every tick of the game and this move vector will be added
     * to the object's location vector.
     *
     * @return Vector3f
     * 		The move vector of the behaviour.
     */
    public abstract Vector3f getMoveVector();

    /**
     * Update the moveVector.
     */
    public void updateMoveVector() {
    }

}
