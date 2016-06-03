package com.github.migi_1.ContextApp.client;

import com.jme3.network.Client;

/**
 * An abstract Client state for the Client.
 */
public abstract class ClientState {
    
    private Client client;
    
    /**
     * Constructor for ClientState.
     * 
     * @param client 
     *      The client that this state represents.
     */
    public ClientState(Client client) {
        this.client = client;
    }
    
    /**
     * Gets the client on which this state applies
     * 
     * @return 
     *      The client on which this state applies
     */
    protected Client getClient() {
        return client;
    }
    
    /**
     * Called when the state of the ClientWrapper changes to this state.
     */
    public abstract void onActivate();
    
    /**
     * Called when the state of the ClientWrapper changes from this state.
     * This can be used to for example, do some cleanup.
     */
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
