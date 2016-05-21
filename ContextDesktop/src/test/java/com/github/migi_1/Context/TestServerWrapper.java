package com.github.migi_1.Context;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.jme3.network.Network;
import com.jme3.network.Server;

/**
 * Test suite for the ServerWrapper class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Network.class)
public class TestServerWrapper {

	/** The port on which the server is running. */
	private static final int PORT = 4321;
	
	private Server server;
	
	/**
	 * Initialises the attributes in this class used for testing.
	 */
	@Before
	public void setUp() {
		server = Mockito.mock(Server.class);
		
		PowerMockito.mockStatic(Network.class);
		try {
			Mockito.when(Network.createServer(PORT)).thenReturn(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks that the getInstance method throws an IllegalStateException if the class hasn't been initialised yet.
	 */
	@Test(expected = IllegalStateException.class)
	public void testGetInstanceNotInitialised() {
		ServerWrapper.getInstance();
	}
	
}
