package com.github.migi_1.ContextApp.client;

import com.jme3.network.Client;

public class InactiveClientState extends ClientState {

    public InactiveClientState(Client client) {
        super(client);
    }
    
    @Override
    public void onActivate() {
        getClient().close();
    }

    @Override
    public void onDeactivate() {
    }
    
    
    
}
