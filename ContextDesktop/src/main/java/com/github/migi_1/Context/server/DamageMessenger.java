package com.github.migi_1.Context.server;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.DamageMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.Server;

/**
 * Creates a DamageMessage and broadcasts it to the android devices.
 */
public class DamageMessenger {
    
    private Main main;
    
    /**
     * Creates a DamageMessenger.
     * @param main 
     *          The instance of the application.
     */
    public DamageMessenger(Main main) {
        this.main = main;
    }
    
    /**
     * This function sends the health value to the right android device.
     */
    public void sendHealth(int health, PlatformPosition pos) {
        DamageMessage message = new DamageMessage(health, pos);
        
        Server server = main.getServer().getServer();
        if (server != null) {
            server.broadcast(message);
        } 
    }
}
