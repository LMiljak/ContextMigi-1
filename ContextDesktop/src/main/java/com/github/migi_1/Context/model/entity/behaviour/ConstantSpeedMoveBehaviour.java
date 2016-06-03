package com.github.migi_1.Context.model.entity.behaviour;

import com.jme3.math.Vector3f;
/**
 * MoveBehaviour for moving at a constant speed at a certain direction.
 * @author Damian
 *
 */
public class ConstantSpeedMoveBehaviour extends EntityMoveBehaviour {


	private float decay;

	/**
	 * Constructor for ConstantSpeedMoveBehaviour.
	 * @param moveVector
	 * 		The direction and speed of this behaviour.
	 */
	public ConstantSpeedMoveBehaviour(Vector3f moveVector) {
		setMoveVector(moveVector);
		this.decay = 1.0f;
	}

    /**
     * Collision has taken place.
     */
    @Override
    public void collided() {

        decay = 0.0f;
    }

    /**
     * MoveVector is updated.
     */
    @Override
    public void updateMoveVector() {

        // regain speed
        if (decay < 1.0f) {
            decay = decay + 0.01f;
        }
        else {
            decay = 1.0f;
        }

    }

    /**
     * Return the decay term.
     * @return decay
     */
    public float getDecay() {
        return decay;
    }

    /**
     * Set the decay term.
     * @param decay Term to set
     */
    public void setDecay(float decay) {
        this.decay = decay;
    }
    
    

}
