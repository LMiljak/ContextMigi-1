package com.github.migi_1.Context.model.entity.behaviour;

import com.jme3.math.Vector3f;

public class TempRotateBehaviour extends RotateBehaviour {

	float i = 0f;
	boolean reverse;
	
	public TempRotateBehaviour() {
		super();
	}
	
	@Override
	public void updateRotateVector() {
		i += 0.01f;
		super.setRotateVector(new Vector3f((float) (Math.cos(i) / 200), 0.0f, (float) (Math.cos(i * 2)) / 200));
	}

}
