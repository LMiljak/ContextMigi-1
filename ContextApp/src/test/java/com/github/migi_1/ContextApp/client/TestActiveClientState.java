package com.github.migi_1.ContextApp.client;

import com.jme3.network.Client;
import org.mockito.Mockito;

public class TestActiveClientState extends TestClientState {

    @Override
    public void setUp() {
        Client client = Mockito.mock(Client.class);
        setClientState(new ActiveClientState(client), client);
    }

    @Override
    public void testOnActivate() {
        getClientState().onActivate();
        
        Mockito.verify(getClientState().getClient(), Mockito.times(1)).start();
    }

    @Override
    public void testOnDeactivate() { }
    
}
