package com.github.migi_1.Context.model.entity.behaviour;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.LineairSpeedMessage;
import com.jme3.math.Vector3f;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 * A Movebehaviour that listens to AccelerometerMessages.
 */
@SuppressWarnings("rawtypes")
//The reason this is implements the networking listener rather than the MessageListener in
//the Messages module, is because it's an abstract class (blame java 1.7), but this class
//already extends MoveBehaviour.
public class LineairSpeedMoveBehaviour extends MoveBehaviour implements MessageListener {

  /** The factor for the accelerometer force for deciding the speed.*/
  private static final float FACTOR = -0.1f;
  private static final float MAX_SPEED = 1.0f;

  private Vector3f moveVector = new Vector3f(0, 0, 0);

  /**
   * Constructor for AccelerometerMoveBehaviour.
   * Also automatically registers this behaviour to the server.
   */
  @SuppressWarnings("unchecked")
  public LineairSpeedMoveBehaviour() {
    Main.getInstance().getServer().getServer().addMessageListener(this);
  }

  @Override
  public Vector3f getMoveVector() {
    return moveVector;
  }

  /**
   * Called when the server receives any message.
   */
  @Override
  public void messageReceived(Object source, Message m) {
      System.out.println("Message recieved");
    if (m instanceof LineairSpeedMessage) { //Check that it's an AccelerometerMessage
        LineairSpeedMessage message = (LineairSpeedMessage) m;
        System.out.println(message.getZ_force());
    }
  }
        
        @Override
        public void updateMoveVector() {
            // NOTHING
        }

}
