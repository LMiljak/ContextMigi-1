package com.github.migi_1.Context;

import com.github.migi_1.ContextMessages.AccelerometerMessage;
import com.github.migi_1.ContextMessages.MessageListener;

/**
 * Responsible for receiving AccelerometerMessages and processing them.
 */
public class AccelerometerMessageHandler extends MessageListener<AccelerometerMessage> {

	/**
	 * Creates and registers a new AccelerometerMessageHandler.
	 * Storing this instance somewhere is useless, as it gets registered
	 * automatically. So all that needs to be done is "new AccelerometerMessageHandler();"
	 * and you're all set.
	 */
	@SuppressWarnings("unchecked")
	public AccelerometerMessageHandler() {
		ServerWrapper.getInstance().getServer().addMessageListener(this);
	}
	
	@Override
	public void messageReceived(Object source, AccelerometerMessage message) {
		//Code here
	}

	@Override
	public Class<AccelerometerMessage> getMessageClass() {
		return AccelerometerMessage.class;
	}

}
