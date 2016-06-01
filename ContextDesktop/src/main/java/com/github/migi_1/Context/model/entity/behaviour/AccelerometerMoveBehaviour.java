package com.github.migi_1.Context.model.entity.behaviour;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.jme3.math.Vector3f;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 * A Movebehaviour that listens to AccelerometerMessages.
 */
public class AccelerometerMoveBehaviour extends MoveBehaviour implements MessageListener {

	/** The factor for the accelerometer force for deciding the speed.*/
	private static final float FACTOR = 0.1f;
	private static final float MAX_SPEED = 1.25f;
	
	private Vector3f moveVector = new Vector3f(0, 0, 0);
	
	public AccelerometerMoveBehaviour() {
		Main.getMain().getServer().getServer().addMessageListener(this);
	}
	
	@Override
	public Vector3f getMoveVector() {
		return moveVector;
	}

	@Override
	public void messageReceived(Object source, Message m) {
		if (m instanceof AccelerometerMessage) {
			AccelerometerMessage message = (AccelerometerMessage) m;
			
			float zSpeed = message.getY_force(); //Y on the gyroscope is Z on JMonkey
			zSpeed *= FACTOR;
			zSpeed = Math.min(zSpeed, MAX_SPEED);
			zSpeed = Math.max(zSpeed, -MAX_SPEED);
			
			this.moveVector = new Vector3f(0, 0, zSpeed);
		}
	}

	@Override
	public void updateMoveVector() { }

}
