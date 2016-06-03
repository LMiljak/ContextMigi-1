package com.github.migi_1.ContextApp.client;

import com.jme3.network.Client;

/**
 * A Client state for when the Client isn't active.
 */
public class InactiveClientState extends ClientState {

    /**
     * Constructor for InactiveClientState.
     * 
     * @param client
     *      The client that this state represents.
     */
    public InactiveClientState(Client client) {
        super(client);
    }
    
    /**
     * Called when this state gets activated.
     * The InactiveClientState closes the client.
     */
    @Override
    public void onActivate() {
        getClient().close();
    }

    @Override
    public void onDeactivate() {
    }
    
    
    
}
