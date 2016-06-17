package com.github.migi_1.Context.model.entity;

import com.github.migi_1.Context.model.entity.behaviour.FlyMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.FlyRotateBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.RotateBehaviour;

/**
 * Represents a camera for flying.
 */
public class FlyCamera extends Camera implements IRotatable {
	
	private FlyRotateBehaviour rotateBehaviour = new FlyRotateBehaviour();
	
	/**
	 * Constructor for FlyCamera.
	 */
	public FlyCamera() {
		super();
		
		setMoveBehaviour(new FlyMoveBehaviour());
	}

	@Override
	public RotateBehaviour getRotateBehaviour() {
		return rotateBehaviour;
	}
	
}
