package com.github.migi_1.ContextApp.client;

import com.jme3.network.Client;
import org.mockito.Mockito;

public class TestInactiveClientState extends TestClientState {

    @Override
    public void setUp() {
        Client client = Mockito.mock(Client.class);
        super.setClientState(new InactiveClientState(client), client);
    }

    @Override
    public void testOnActivate() {
        super.getClientState().onActivate();
        
        Mockito.verify(getClientState().getClient(), Mockito.times(1)).close();
    }

    @Override
    public void testOnDeactivate() { }
    
    
    
}
