package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.HealthMessage;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.PlatformPosition;

/**
 * Responsible for receiving AccelerometerMessages and processing them.
 */
public class HealthMessageHandler extends MessageListener<HealthMessage> {
    
    private MainActivity main;
    
    /**
     * Creates and registers a new HealthMessageHandler.
     * @param main
     * 		The instance of the application.
     */
    public HealthMessageHandler(MainActivity main) {
        this.main = main;
    }
    
    /**
     * Handles a message when it is received.
     * @param source
     *              the source of the message
     * @param message 
     *              the message itself
     */
    @Override
    public void messageReceived(Object source, HealthMessage message) {
        PlatformPosition position = message.getPos();
        if (position == main.getPosHolder().getPosition()) {
            main.setHealth(message.getHealth());
        }
    }
    
    /**
     * Returns the HealthMessage class.
     * @return HealthMessage.class Class<HealthMessage>
     */
    @Override
    public Class<HealthMessage> getMessageClass() {
        return HealthMessage.class;
    }
}
