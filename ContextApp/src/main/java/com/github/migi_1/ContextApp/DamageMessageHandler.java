package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.DamageMessage;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.PlatformPosition;

/**
 * Responsible for receiving AccelerometerMessages and processing them.
 */
public class DamageMessageHandler extends MessageListener<DamageMessage> {
    
    private MainActivity main;
    
    /**
     * Creates and registers a new DamageMessageHandler.
     * @param main
     * 		The instance of the application.
     */
    public DamageMessageHandler(MainActivity main) {
        this.main = main;
    }
    
    @Override
    public void messageReceived(Object source, DamageMessage message) {
        PlatformPosition position = message.getPos();
        if (position == main.getPosHolder().getPosition()) {
            main.setHealth(message.getHealth());
        }
    }
    
    @Override
    public Class<DamageMessage> getMessageClass() {
        return DamageMessage.class;
    }
}
