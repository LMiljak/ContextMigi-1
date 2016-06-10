package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.HitMissMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.Server;

/**
 * This class creates a HitMissMessage and sends it to the appropriate device.
 */
public class HitMissMessenger {
    
    private Main main;
    
    /**
     * Creates a HitMissMessenger.
     * @param main 
     *          The instance of the application.
     */
    public HitMissMessenger(Main main) {
        this.main = main;
    }
    
    /**
     * This function sends whether or not the player hit an enemy 
     * to the right android device.
     * @param hit
     * 			whether or not the player has hit an enemy.
     * @param pos
     * 			the position of the attacking player.
     */
    public void sendHitMiss(boolean hit, PlatformPosition pos) {
        HitMissMessage message = new HitMissMessage(hit, pos);
        
        ServerWrapper serverWrapper = main.getServer();
        Server server = serverWrapper.getServer();
        if (server != null) {
            server.broadcast(message);
        } 
    }
    
}
