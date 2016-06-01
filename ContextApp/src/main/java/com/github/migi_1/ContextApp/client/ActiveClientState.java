package com.github.migi_1.ContextApp.client;

import com.jme3.network.Client;

public class ActiveClientState extends ClientState {

    public ActiveClientState(Client client) {
        super(client);
    }
    
    @Override
    public void onActivate() {
        getClient().start();
    }

    @Override
    public void onDeactivate() {
    }
    
}
