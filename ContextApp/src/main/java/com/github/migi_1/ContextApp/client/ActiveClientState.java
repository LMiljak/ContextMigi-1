package com.github.migi_1.ContextApp.client;

import com.jme3.network.Client;

/**
 * A client state for when the client is active.
 */
public class ActiveClientState extends ClientState {

    /**
     * Constructor for ActiveClientState.
     * 
     * @param client
     *      The client that this state represents.
     */
    public ActiveClientState(Client client) {
        super(client);
    }
    
    /**
     * Called when the state of the ClientWrapper changes to this state.
     * The ActiveClientState starts the client.
     */
    @Override
    public void onActivate() {
        getClient().start();
    }

    @Override
    public void onDeactivate() {
    }
    
}
