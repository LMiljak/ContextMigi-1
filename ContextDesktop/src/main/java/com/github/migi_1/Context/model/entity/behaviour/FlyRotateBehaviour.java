package com.github.migi_1.Context.model.entity.behaviour;

import com.github.migi_1.Context.main.InputHandler;
import com.github.migi_1.Context.main.KeyInputListener;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;

/**
 * A Rotate behaviour that listens to keys (a and d) to rotate left and right.
 */
public class FlyRotateBehaviour extends RotateBehaviour implements KeyInputListener {

	private boolean left, right = false;
	
	/**
	 * Constructor for FlyRotateBehaviour.
	 */
	public FlyRotateBehaviour() {
		final int[] keys = {KeyInput.KEY_A, KeyInput.KEY_D};
		for (int key : keys) {
			InputHandler.getInstance().register(this, key);
		}
	}
	
	@Override
	public void onKeyPressed(int key) {
		switch (key) {
		case KeyInput.KEY_A:
			left = true;
			break;
		case KeyInput.KEY_D:
			right = true;
			break;
		default:
		}
	}

	@Override
	public void onKeyReleased(int key) {
		switch (key) {
		case KeyInput.KEY_A:
			left = false;
			break;
		case KeyInput.KEY_D:
			right = false;
			break;
		default:
		}
	}

	@Override
	public void updateRotateVector() {
		final float rotateSpeed = 0.01f;
		
		Vector3f result = Vector3f.ZERO;
		if (left) {
			result = result.add(new Vector3f(0, rotateSpeed, 0));
		}
		if (right) {
			result = result.add(new Vector3f(0, -rotateSpeed, 0));
		}
		
		setRotateVector(result);
	}

}
