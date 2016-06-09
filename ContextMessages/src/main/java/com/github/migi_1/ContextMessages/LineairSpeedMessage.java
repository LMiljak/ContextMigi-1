package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * A message class that contains data about the accelerator
 * of the Android device.
 */
@Serializable
public class LineairSpeedMessage extends AbstractMessage {

  private float zForce;
  
  /**
   * Public empty constructor used by the JME3 networking
   * library.
   */
  public LineairSpeedMessage() { }
  
  /**
   * Creates a new AcceleratorMessage.
   * 
   * @param xForce
   *    Acceleration force along the x axis (including gravity).
   * @param yForce
   *    Acceleration force along the y axis (including gravity).
   * @param zForce
   *    Acceleration force along the z axis (including gravity).
   */
  public LineairSpeedMessage(float zForce) {
    this.zForce = zForce;
  }

  /**
   * Gets the acceleration force along the z axis (including gravity).
   * 
   * @return
   *    The acceleration force along the z axis (including gravity).
   */
  public float getZ_force() {
    return zForce;
  }
  
}
