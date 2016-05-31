package com.github.migi_1.ContextApp;

import android.app.Activity;
import com.github.migi_1.ContextMessages.AttackMessage;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.Client;

/**
 *
 * @author Remi
 */
public class AttackMessenger extends Activity {
    
    private MainActivity act;
    
    /**
     * Creates an AttackMessenger.
     * @param act 
     *          The MainActivity from which the messenger is created.
     */
    public AttackMessenger(MainActivity act){
        this.act = act;
    }
    
    public void sendAttack(PlatformPosition pos, String dir) {
        AttackMessage message = new AttackMessage(pos, dir);
        
        Client client = ClientWrapper.getInstance().getClient();
        if (client != null) {
            client.send(message);
        }
    }
    
}
