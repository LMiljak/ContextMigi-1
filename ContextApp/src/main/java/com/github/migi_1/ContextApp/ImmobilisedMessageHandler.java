package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.ImmobilisedMessage;
import com.github.migi_1.ContextMessages.MessageListener;

/**
 * Handler for ImmobobilisedMessage objects.
 * 
 * @author Marcel
 */
public class ImmobilisedMessageHandler extends MessageListener<ImmobilisedMessage> {
    
    private MainActivity main;
    
    /**
     * Contstructor.
     * @param main the active MainActivity
     */
    public ImmobilisedMessageHandler(MainActivity main) { 
        this.main = main;
        main.getClient().getClient().addMessageListener(this);
    }

    /**
     * Pass immobilisation information to the main activity.
     * @param source Source of the message.
     * @param message ImmobilisedMessage
     */
    @Override
    public void messageReceived(Object source, ImmobilisedMessage message) {
        if (main.getPosHolder().getPosition() == message.getPosition()) {
            if (message.getImmobilised()) {
                main.immobilise(true);
            }
            else {
                main.immobilise(false);
            }
        }
    }

    @Override
    public Class<ImmobilisedMessage> getMessageClass() { 
        return ImmobilisedMessage.class;
    }

    
    
}
