package com.github.migi_1.Context;

import org.mockito.Mockito;

import com.jme3.network.Server;

/**
 * Test suite for the InactiveServerState class.
 */
public class TestInactiveServerState extends TestServerState {

	@Override
	public void setUp() {
		Server server = Mockito.mock(Server.class);
		
		super.setServerState(new InactiveServerState(server), server);
	}

	@Override
	public void testOnActivate() {
		super.getServerState().getServer().isRunning();
	}

	@Override
	public void testOnDeactivate() { }
}
