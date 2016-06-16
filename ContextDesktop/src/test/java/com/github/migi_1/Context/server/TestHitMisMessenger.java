package com.github.migi_1.Context.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.Server;

public class TestHitMisMessenger {

    private HitMissMessenger hitMissMessenger;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;

    @Before
    public void setUp() throws Exception {
        main = Mockito.mock(Main.class);
        serverWrapper = Mockito.mock(ServerWrapper.class);
        server = Mockito.mock(Server.class);

        Mockito.when(main.getServer()).thenReturn(serverWrapper);
        Mockito.when(serverWrapper.getServer()).thenReturn(server);
        Mockito.doNothing().when(server).broadcast(Mockito.any());

        hitMissMessenger = new HitMissMessenger(main);
    }

    @Test
    public void sendHitMissTest() {
        hitMissMessenger.sendHitMiss(true, PlatformPosition.FRONTLEFT);
    }

}
