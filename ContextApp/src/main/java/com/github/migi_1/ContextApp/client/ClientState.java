package com.github.migi_1.ContextApp.client;

import com.jme3.network.Client;

public abstract class ClientState {
    
    private Client client;
    
    public ClientState(Client client) {
        this.client = client;
    }
    
    protected Client getClient() {
        return client;
    }
    
    public abstract void onActivate();
    
    public abstract void onDeactivate();
    
    @Override
    public int hashCode() {
        return client.hashCode() + getClass().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClientState other = (ClientState) obj;
        
        return client.equals(other.client);
    }
}
