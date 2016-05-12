package com.github.migi_1.ContextApp;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Test suite for the AutoConector class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AutoConnector.class, ServerFinder.class})
public class TestAutoConnector {
    
    private AutoConnector autoConnector;
    private ClientWrapper client;
    private ExecutorService executorService;
    
    /**
     * Initialises the private fields used for the test cases.
     */
    @Before
    public void setup() {
        client = Mockito.spy(ClientWrapper.getInstance());
        executorService = Executors.newFixedThreadPool(1);
        autoConnector = PowerMockito.spy(new AutoConnector(executorService, client)); 
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
    public void testAutoStart() throws Exception {
        ServerDiscoveryHandler fakeConnector = Mockito.mock(ServerDiscoveryHandler.class);
        PowerMockito.doReturn(fakeConnector).when(autoConnector, "getConnector");
        
        ServerFinder fakeServerFinder = Mockito.mock(ServerFinder.class);
        PowerMockito.mockStatic(ServerFinder.class);
        Mockito.when(ServerFinder.getInstance()).thenReturn(fakeServerFinder);
        
        autoConnector.autoStart();
        
        Mockito.verify(fakeServerFinder, Mockito.times(1)).findServers(executorService, fakeConnector);
    }
    
    
    
}
