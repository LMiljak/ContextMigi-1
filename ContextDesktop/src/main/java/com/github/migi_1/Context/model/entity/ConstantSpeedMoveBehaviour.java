package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;
/**
 * MoveBehaviour for moving at a constant speed at a certain direction.
 * @author Damian
 *
 */
public class ConstantSpeedMoveBehaviour extends MoveBehaviour {

	private Vector3f moveVector;

	private float decay;

	/**
	 * Constructor for ConstantSpeedMoveBehaviour
	 *
	 * @param moveVector
	 * 		The direction and speed of this behaviour.
	 */
	public ConstantSpeedMoveBehaviour(Vector3f moveVector) {
		this.moveVector = moveVector;
		this.decay = 1.0f;
	}

    @Override
    public Vector3f getMoveVector() {
        return moveVector.mult(decay);
    }

    public void collided() {
        decay = 0.0f;
    }

    @Override
    public void updateMoveVector() {

    }
}
