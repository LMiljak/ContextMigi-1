package com.github.migi_1.ContextMessages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * A message class that contains data about the accelerator
 * of the Android device.
 */
@Serializable
public class AccelerometerMessage extends AbstractMessage {

	private Vector3f forces;
	
	/**
	 * Public empty constructor used by the JME3 networking
	 * library.
	 */
	public AccelerometerMessage() { }
	
	/**
	 * Creates a new AcceleratorMessage.
	 * 
	 * @param forces
	 * 		The accelerating force along each dimension (x,y,z).
	 */
	public AccelerometerMessage(Vector3f forces) {
		this.forces = forces;
	}

	/**
	 * Gets the accelerating force along each dimension (x,y,z).
	 * 
	 * @return
	 * 		The accelerating force along each dimension (x,y,z).
	 */
	public Vector3f getForces() {
		return forces;
	}
	
}
