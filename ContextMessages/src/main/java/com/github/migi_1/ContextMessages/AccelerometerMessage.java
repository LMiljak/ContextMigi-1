package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * A message class that contains data about the accelerator
 * of the Android device.
 */
@Serializable
public class AccelerometerMessage extends AbstractMessage {

	private float xForce;
	private float yForce;
	private float zForce;
	
	/**
	 * Public empty constructor used by the JME3 networking
	 * library.
	 */
	public AccelerometerMessage() { }
	
	/**
	 * Creates a new AcceleratorMessage.
	 * 
	 * @param xForce
	 * 		Acceleration force along the x axis (including gravity).
	 * @param yForce
	 * 		Acceleration force along the y axis (including gravity).
	 * @param zForce
	 * 		Acceleration force along the z axis (including gravity).
	 */
	public AccelerometerMessage(float xForce, float yForce, float zForce) {
		this.xForce = xForce;
		this.yForce = yForce;
		this.zForce = zForce;
	}

	/**
	 * Gets the acceleration force along the x axis (including gravity).
	 * 
	 * @return
	 * 		The acceleration force along the x axis (including gravity).
	 */
	public float getX_force() {
		return xForce;
	}

	/**
	 * Gets the acceleration force along the y axis (including gravity).
	 * 
	 * @return
	 * 		The acceleration force along the y axis (including gravity).
	 */
	public float getY_force() {
		return yForce;
	}

	/**
	 * Gets the acceleration force along the z axis (including gravity).
	 * 
	 * @return
	 * 		The acceleration force along the z axis (including gravity).
	 */
	public float getZ_force() {
		return zForce;
	}
	
}
