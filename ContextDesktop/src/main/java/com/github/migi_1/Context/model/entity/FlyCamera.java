package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.model.entity.behaviour.FlyMoveBehaviour;

/**
 * Represents a camera for flying.
 */
public class FlyCamera extends Camera {
	
	/**
	 * Constructor for FlyCamera.
	 */
	public FlyCamera() {
		super();
		
		setMoveBehaviour(new FlyMoveBehaviour());
	}
	
}
