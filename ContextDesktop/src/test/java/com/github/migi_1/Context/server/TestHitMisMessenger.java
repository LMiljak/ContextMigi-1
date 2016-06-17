package com.github.migi_1.Context.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.main.Main;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.jme3.network.AbstractMessage;
import com.jme3.network.Server;

/**
 * Tests everything that has to with the HitMistMessenger.
 * @author Nils
 *
 */
public class TestHitMisMessenger {

    private HitMissMessenger hitMissMessenger;
    private Main main;
    private ServerWrapper serverWrapper;
    private Server server;

    /**
     * This method starts every time a new test case starts.
     * @throws Exception exception that is thrown.
     */
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

    /**
     * Tests the message is broadcasted when the hitMiss method is called. 
     */
    @Test
    public void sendHitMissTest() {
        hitMissMessenger.sendHitMiss(true, PlatformPosition.FRONTLEFT);
        Mockito.verify(server, Mockito.times(1)).broadcast(Mockito.any(AbstractMessage.class));
    }

}
