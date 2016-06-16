package com.github.migi_1.Context.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

public class TestCarrierAssigner {

    private CarrierAssigner carrierAssigner;
    private Platform platform;
    private ServerWrapper serverWrapper;
    private Server server;
    private MainEnvironment mainEnv;
    private HostedConnection connection;
    private String ipAdress = "IP";

    @Before
    public void setUp() throws Exception {
        connection = Mockito.mock(HostedConnection.class);
        mainEnv = Mockito.mock(MainEnvironment.class);
        platform = Mockito.mock(Platform.class);
        server = Mockito.mock(Server.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);

        Mockito.when(serverWrapper.getServer()).thenReturn(server);
        Mockito.when(connection.getAddress()).thenReturn(ipAdress);

        carrierAssigner = new CarrierAssigner(serverWrapper);
    }

    @Test
    public void connectionAddedTest() {
        assertEquals("", carrierAssigner.getAddress(PlatformPosition.FRONTLEFT));
        carrierAssigner.connectionAdded(server, connection);
        Mockito.verify(connection).send(Mockito.any());
        assertEquals(ipAdress, carrierAssigner.getAddress(PlatformPosition.FRONTLEFT));
    }


    @Test
    public void getAdressTest() {
        assertEquals("", carrierAssigner.getAddress(PlatformPosition.FRONTLEFT));
    }

    @Test
    public void connectionAddedTwiceTest() {
        carrierAssigner.connectionAdded(server, connection);
        carrierAssigner.connectionAdded(server, connection);
        assertEquals(ipAdress, carrierAssigner.getAddress(PlatformPosition.FRONTLEFT));
        assertEquals(ipAdress, carrierAssigner.getAddress(PlatformPosition.FRONTRIGHT));
    }

}
