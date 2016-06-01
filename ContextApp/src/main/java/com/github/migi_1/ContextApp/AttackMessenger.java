package com.github.migi_1.ContextApp;

import android.app.Activity;
import android.util.Log;
import com.github.migi_1.ContextMessages.AttackMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.Client;

/**
 * An activity that sends AttackMessages to the server.
 */
public class AttackMessenger {
    
    private MainActivity act;
    
    /**
     * Creates an AttackMessenger.
     * @param act 
     *          The MainActivity from which the messenger is created.
     */
    public AttackMessenger(MainActivity act) {
        this.act = act;
    }
    
    /**
     * Creates an AttackMessage and sends it to the server.
     * @param pos
     * 			the PlatformPosition of the player who's attacking.
     * @param dir
     * 			the direction in which the player is attacking (String).
     */
    public void sendAttack(PlatformPosition pos, String dir) {
        Log.d("attack", dir);
        AttackMessage message = new AttackMessage(pos, dir);
        
        Client client = ClientWrapper.getInstance().getClient();
        if (client != null) {
            client.send(message);
        }
    }
    
}
