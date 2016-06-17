package com.github.migi_1.Context.model.entity;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

/**
 * Tests everything that has to with the CarrierAssigner.
 * @author Nils
 *
 */
public class TestCarrierAssigner {

    private CarrierAssigner carrierAssigner;
    private ServerWrapper serverWrapper;
    private Server server;
    private HostedConnection connection;
    private String ipAdress = "IP";
    private HashMap<PlatformPosition, HostedConnection> carrierMap;
    
    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        connection = Mockito.mock(HostedConnection.class);
        server = Mockito.mock(Server.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        carrierMap = Mockito.mock(HashMap.class);

        Mockito.when(serverWrapper.getServer()).thenReturn(server);
        Mockito.when(connection.getAddress()).thenReturn(ipAdress);

        carrierAssigner = new CarrierAssigner(serverWrapper);
    }
    
    /**
     * Tests if the correct carrier was assigned when a connection has been made.
     */
    @Test
    public void connectionAddedTest() {
        assertEquals("", carrierAssigner.getAddress(PlatformPosition.FRONTLEFT));
        carrierAssigner.connectionAdded(server, connection);
        Mockito.verify(connection).send(Mockito.any());
        assertEquals(ipAdress, carrierAssigner.getAddress(PlatformPosition.FRONTLEFT));
    }

    /**
     * Tests the getAdress method.
     */
    @Test
    public void getAdressTest() {
        assertEquals("", carrierAssigner.getAddress(PlatformPosition.FRONTLEFT));
    }
    
    /**
     * Tests when 2 players join, their carriers are assigned the correct position.
     */
    @Test
    public void connectionAddedTwiceTest() {
        carrierAssigner.connectionAdded(server, connection);
        carrierAssigner.connectionAdded(server, connection);
        assertEquals(ipAdress, carrierAssigner.getAddress(PlatformPosition.FRONTLEFT));
        assertEquals(ipAdress, carrierAssigner.getAddress(PlatformPosition.FRONTRIGHT));
    }
    
    /**
     * Tests if a connection is lost the carrier gets removed.
     */
    @Test
    public void connectionRemovedTest() {
        carrierAssigner.setCarrierAdressMap(carrierMap);
        carrierAssigner.connectionRemoved(server, connection);
        Mockito.verify(carrierMap, Mockito.times(4)).remove(Mockito.any(), Mockito.any());
    }

}
