package com.github.migi_1.Context.model.entity.behaviour;

import com.github.migi_1.Context.main.InputHandler;
import com.github.migi_1.Context.main.KeyInputListener;
import com.jme3.input.KeyInput;

import jmevr.app.VRApplication;

/**
 * A MoveBehaviour that listens to keyinputs de determine the direction to fly.
 */
public class FlyMoveBehaviour extends MoveBehaviour implements KeyInputListener {

	private boolean forwards, back = false;
	
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
		case KeyInput.KEY_D:
			back = true;
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
		case KeyInput.KEY_D:
			back = false;
			break;
		default:
		}
	}

	@Override
	public void updateMoveVector() {
		if (forwards) {
            setMoveVector(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(0.1f));
        }
        if (back) {
        	setMoveVector(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(-0.1f));
        }
	}

}
