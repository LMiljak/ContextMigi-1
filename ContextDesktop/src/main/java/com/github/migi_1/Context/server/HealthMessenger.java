package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.HealthMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.Server;

/**
 * Creates a HealthMessage and broadcasts it to the android devices.
 */
public class HealthMessenger {
    
    private Main main;
    
    /**
     * Creates a HealthMessenger.
     * @param main 
     *          The instance of the application.
     */
    public HealthMessenger(Main main) {
        this.main = main;
    }
    
    /**
     * This function sends the new health value to the right android device.
     */
    public void sendHealth(int health, PlatformPosition pos) {
        HealthMessage message = new HealthMessage(health, pos);
        
        Server server = main.getServer().getServer();
        if (server != null) {
            server.broadcast(message);
        } 
    }
}
