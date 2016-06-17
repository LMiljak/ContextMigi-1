package com.github.migi_1.ContextApp;

import com.github.migi_1.ContextApp.client.ClientWrapper;
import com.github.migi_1.ContextMessages.AttackMessage;
import com.github.migi_1.ContextMessages.Direction;
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
     * @param position
     * 			the PlatformPosition of the player who's attacking.
     * @param direction
     * 			the direction in which the player is attacking (String).
     */
    public void sendAttack(PlatformPosition position, Direction direction) {
        AttackMessage message = new AttackMessage(position, direction);

        ClientWrapper clientWrapper = act.getClient();
        Client client = clientWrapper.getClient();
        if (client.isStarted()) {
            clientWrapper.getClient().send(message);
        }
    }

}
