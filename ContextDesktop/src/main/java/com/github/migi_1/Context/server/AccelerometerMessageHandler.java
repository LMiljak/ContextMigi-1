package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.github.migi_1.ContextMessages.MessageListener;

/**
 * Responsible for receiving AccelerometerMessages and processing them.
 */
public class AccelerometerMessageHandler extends MessageListener<AccelerometerMessage> {

    private Main main;
    
	/**
	 * Creates and registers a new AccelerometerMessageHandler.
	 * 
	 * @param main
	 * 		The instance of the application.
	 */
	public AccelerometerMessageHandler(Main main) {
		this.main = main;
	}

        
        
	@Override
	public void messageReceived(Object source, AccelerometerMessage message) {
        float zValue = message.getY_force();
        zValue = zValue / 5;
        if (zValue < -1.0f) {
            zValue = -1.0f;
        }
        if (zValue > 1.0f) {
            zValue = 1.0f;
        }
	    main.handleAccelerometerMessage(zValue);
	}

	@Override
	public Class<AccelerometerMessage> getMessageClass() {
		return AccelerometerMessage.class;
	}

}
