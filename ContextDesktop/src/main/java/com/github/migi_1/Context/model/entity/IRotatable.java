package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.model.entity.behaviour.RotateBehaviour;
import com.jme3.math.Vector3f;

/**
 * Interface for displayables that can also rotate.
 */
public interface IRotatable extends IDisplayable {

	/**
	 * Gets the rotateBehaviour of this Rotatable.
	 * 
	 * @return
	 * 		The rotateBehaviour for this Rotatable.
	 */
	RotateBehaviour getRotateBehaviour();
	
	/**
	 * Rotates this Rotatable.
	 */
	default void rotate() {
		Vector3f rotateVector = getRotateBehaviour().getRotateVector();
		getModel().rotate(rotateVector.x, rotateVector.y, rotateVector.z);
	}
}
