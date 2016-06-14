package com.github.migi_1.ContextApp;

import android.media.SoundPool;
import android.util.Log;
import com.github.migi_1.ContextMessages.HitMissMessage;
import com.github.migi_1.ContextMessages.MessageListener;
import com.github.migi_1.ContextMessages.PlatformPosition;

/**
 * This class handles HitMissMessages and gives the player cooldown depending
 * on whether their attack hit or not and plays the right sound effect.
 */
public class HitMissMessageHandler extends MessageListener<HitMissMessage> {
    
    private MainActivity main;
    
    /**
     * Creates and registers a new HitMissMessageHandler.
     * @param main
     * 		The instance of the application.
     */
    public HitMissMessageHandler(MainActivity main) {
        this.main = main;
        main.getClient().getClient().addMessageListener(this);
    }
    
    /**
     * Handles a message when it is received.
     * @param source
     *              the source of the message
     * @param message 
     *              the message itself
     */
    @Override
    public void messageReceived(Object source, HitMissMessage message) {
        PlatformPosition position = message.getPos();
        if (position == main.getPosHolder().getPosition()) {
            main.hitMiss(message.getHit());
        }
    }
    
    /**
     * Returns the HitMissMessage class.
     * @return HitMissMessage.class Class<HealthMessage>
     */
    @Override
    public Class<HitMissMessage> getMessageClass() {
        return HitMissMessage.class;
    }
    
}