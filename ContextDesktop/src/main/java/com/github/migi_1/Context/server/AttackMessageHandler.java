package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.AttackMessage;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.PlatformPosition;

/**
 * Handles the AttackMessages when they are received.
 */
public class AttackMessageHandler extends MessageListener<AttackMessage> {
    
    private Main main;
    
    /**
     * Creates an AttackMessageHandler.
     * @param main
     * 			the main application by which this function is called
     */
    @SuppressWarnings("unchecked")
	public AttackMessageHandler(Main main) {
        this.main = main;
        main.getServer().getServer().addMessageListener(this);
    }
    
    /**
     * Processes AttackMessages when they are received.
     * @param source
     *              the source of the message
     * @param message
     *              the message itself
     */
    @Override
    public void messageReceived(Object source, AttackMessage message) {
        PlatformPosition pos = message.getPosition();
        String dir = message.getDirection();
        
        main.handleAttack(pos, dir);
    }
    
    @Override
    public Class<AttackMessage> getMessageClass() {
        return AttackMessage.class;
    }
}
