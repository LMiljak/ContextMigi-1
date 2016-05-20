package com.github.migi_1.Context;

import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.github.migi_1.ContextMessages.MessageListener;

/**
 * Responsible for receiving AccelerometerMessages and processing them.
 */
public class AccelerometerMessageHandler extends MessageListener<AccelerometerMessage> {

    private Main main;
    
	/**
	 * Creates and registers a new AccelerometerMessageHandler.
	 * Storing this instance somewhere is useless, as it gets registered
	 * automatically. So all that needs to be done is "new AccelerometerMessageHandler();"
	 * and you're all set.
	 */
	@SuppressWarnings("unchecked")
	public AccelerometerMessageHandler(Main main) {
		ServerWrapper.getInstance().getServer().addMessageListener(this);
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
