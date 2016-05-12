package com.github.migi_1.ContextApp;

import com.jme3.network.Network;
import com.jme3.network.Server;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

/**
 * Test suite for the AutoConector class.
 */
public class TestAutoConnector {
    
    private AutoConnector autoConnector;
    private ClientWrapper client;
    
    /**
     * Initialises the private fields used for the test cases.
     */
    @Before
    public void setup() {
        client = Mockito.spy(ClientWrapper.getInstance());
        autoConnector = new AutoConnector(Executors.newFixedThreadPool(1), client); 
    }
    
    /**
     * Asserts that the ServerDicoveryHandler that the private getConncetor
     * method returns, attempts to connect to a found server.
     */
    @Test
    public void testGetConnector() throws Exception {
        ServerDiscoveryHandler connector = Whitebox.invokeMethod(autoConnector, "getConnector");
        
        InetAddress fakeAddress = Mockito.mock(InetAddress.class);
        Mockito.when(fakeAddress.getHostAddress()).thenReturn("fakeAddress");
        
        Mockito.doNothing().when(client).startClient("fakeAddress");
        
        connector.onServerDiscovery(fakeAddress);
        Mockito.verify(client, Mockito.times(1)).startClient("fakeAddress");
    }
    
    /**
     * Tests the autoStart method.
     */
    @Test
    public void testAutoStart() throws IllegalArgumentException, IllegalAccessException, IOException {
        //TODO
    }
    
    
    
}
