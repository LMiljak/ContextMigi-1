package com.github.migi_1.Context.model.entity.behaviour;

import com.github.migi_1.Context.main.InputHandler;
import com.github.migi_1.Context.main.KeyInputListener;
import com.jme3.input.KeyInput;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import jmevr.app.VRApplication;

/**
 * A MoveBehaviour that listens to keyinputs to determine the direction to fly.
 */
public class FlyMoveBehaviour extends MoveBehaviour implements KeyInputListener {

	private boolean forwards, back, up, down = false;
	
	/**
	 * Constructor for FlyMoveBehaviour.
	 */
	public FlyMoveBehaviour() {
		int[] keys = {KeyInput.KEY_W, KeyInput.KEY_S, 
				KeyInput.KEY_LSHIFT, KeyInput.KEY_LCONTROL};
		for (int key : keys) {
			InputHandler.getInstance().register(this, key);
		}
	}
	
	@Override
	public void onKeyPressed(int key) {
		switch (key) {
		case KeyInput.KEY_W:
			forwards = true;
			break;
		case KeyInput.KEY_S:
			back = true;
			break;
		case KeyInput.KEY_LSHIFT:
			up = true;
			break;
		case KeyInput.KEY_LCONTROL:
			down = true;
			break;
		default:
		}
	}

	@Override
	public void onKeyReleased(int key) {
		switch (key) {
		case KeyInput.KEY_W:
			forwards = false;
			break;
		case KeyInput.KEY_S:
			back = false;
			break;
		case KeyInput.KEY_LSHIFT:
			up = false;
			break;
		case KeyInput.KEY_LCONTROL:
			down = false;
			break;
		default:
		}
	}

	@Override
	public void updateMoveVector() {
		Vector3f result = Vector3f.ZERO;
		
		Quaternion rotation = VRApplication.getFinalObserverRotation();
		final float mult = 0.1f;
		if (forwards) {
            result = result.add(rotation.getRotationColumn(2).mult(mult));
        } 
		if (back) {
        	result = result.add(rotation.getRotationColumn(2).mult(-mult));
        }
		if (up) {
			result = result.add(rotation.getRotationColumn(1).mult(mult));
		}
		if (down) {
			result = result.add(rotation.getRotationColumn(1).mult(-mult));
		}
		
		setMoveVector(result);
	}

}
