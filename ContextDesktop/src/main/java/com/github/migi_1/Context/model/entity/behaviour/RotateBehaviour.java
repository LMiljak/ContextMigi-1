package com.github.migi_1.Context.model.entity.behaviour;

import com.jme3.math.Vector3f;

/**
 * A behaviour for objects that implement the Rotatable interface.
 */
public abstract class RotateBehaviour {

	private Vector3f rotateVector;
	
	/**
	 * Constructor for RotateBehaviour. The initial rotate vector is (0, 0, 0).
	 */
	public RotateBehaviour() {
		this.rotateVector = Vector3f.ZERO;
	}
	
	/**
	 * Gets the rotate vector of this behaviour.
	 * 
	 * @return
	 * 		The rotate vector of this behaviour.
	 */
	public Vector3f getRotateVector() {
		return rotateVector;
	}
	
	/**
	 * Sets the rotate vector of this behaviour.
	 * 
	 * @param rotateVector
	 * 		The new rotate vector.
	 */
	public void setRotateVector(Vector3f rotateVector) {
		this.rotateVector = rotateVector;
	}
	
	/**
	 * Updates the rotate vector. Called every game tick by the environment.
	 */
	public abstract void updateRotateVector();
}
