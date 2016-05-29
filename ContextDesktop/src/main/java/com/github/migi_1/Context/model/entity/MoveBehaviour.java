package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

/**
 * Abstract behaviour for objects that can move in the Environment.
 * @author Damian
 *
 */
public abstract class MoveBehaviour {

    private Vector3f moveVector;

    /**
     * Constructor for abstract MoveBehaviour where the default moveVector is static.
     */
    public MoveBehaviour() {
        moveVector = new Vector3f(0, 0, 0);
    }

    /**
     * Gets the move vector of this behaviour.
     * This method is called every tick of the game and this move vector will be added
     * to the object's location vector.
     *
     * @return Vector3f
     * 		The move vector of the behaviour.
     */
    public Vector3f getMoveVector() {
        return moveVector;
    }

    /**
     * Update the moveVector.
     */
    public void updateMoveVector() { }

    /**
     * Setter for the MoveVector attribute.
     * @param moveVector attribute
     */
    public void setMoveVector(Vector3f moveVector) {
        this.moveVector = moveVector;
    }



}
