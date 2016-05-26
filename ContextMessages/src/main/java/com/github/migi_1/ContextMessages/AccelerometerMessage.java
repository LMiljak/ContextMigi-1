package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * A message class that contains data about the accelerator
 * of the Android device.
 */
@Serializable
public class AccelerometerMessage extends AbstractMessage {

	private float x_force;
	private float y_force;
	private float z_force;
	
	/**
	 * Public empty constructor used by the JME3 networking
	 * library.
	 */
	public AccelerometerMessage() { }
	
	/**
	 * Creates a new AcceleratorMessage.
	 * 
	 * @param x_force
	 * 		Acceleration force along the x axis (including gravity).
	 * @param y_force
	 * 		Acceleration force along the y axis (including gravity).
	 * @param z_force
	 * 		Acceleration force along the z axis (including gravity).
	 */
	public AccelerometerMessage(float x_force, float y_force, float z_force) {
		this.x_force = x_force;
		this.y_force = y_force;
		this.z_force = z_force;
	}

	/**
	 * Gets the acceleration force along the x axis (including gravity).
	 * 
	 * @return
	 * 		The acceleration force along the x axis (including gravity).
	 */
	public float getX_force() {
		return x_force;
	}

	/**
	 * Gets the acceleration force along the y axis (including gravity).
	 * 
	 * @return
	 * 		The acceleration force along the y axis (including gravity).
	 */
	public float getY_force() {
		return y_force;
	}

	/**
	 * Gets the acceleration force along the z axis (including gravity).
	 * 
	 * @return
	 * 		The acceleration force along the z axis (including gravity).
	 */
	public float getZ_force() {
		return z_force;
	}
	
}
