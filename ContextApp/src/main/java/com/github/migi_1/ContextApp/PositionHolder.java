package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.PositionMessage;

/**
 * Singleton class that acts as a global variabele to store the
 * position this Client has been assigned. It also listens for messages
 * from the Server about the position.
 */
public class PositionHolder extends MessageListener<PositionMessage> {

    private static final PositionHolder INSTANCE = new PositionHolder();
    
    private PlatformPosition position;
    
    /** Private empty constructor to prevent initialisation.*/
    private PositionHolder() { }
    
    /**
     * Gets the instance of this singleton class.
     * 
     * @return 
     *      The instance of this class.
     */
    public static PositionHolder getInstance() {
        return INSTANCE;
    }
    
    /**
     * Called when a PositionMessage sent by the server has been received.
     * 
     * Sets the position of this client using the position assigned on the
     * message.
     * 
     * @param source
     *      The source that sent the message.
     * @param message 
     *      The message sent.
     */
    @Override
    public void messageReceived(Object source, PositionMessage message) {
        this.position = message.getPosition();
    }

    @Override
    public Class<PositionMessage> getMessageClass() {
        return PositionMessage.class;
    }
    
    /**
     * Gets the position of this client.
     * 
     * @return
     *      The position of this client.
     */
    public PlatformPosition getPosition() {
        return position;
    }
    
    
    
}
