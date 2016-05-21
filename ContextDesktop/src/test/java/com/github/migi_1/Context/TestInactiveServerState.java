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
		super.getServerState().onActivate();
		
		//Verify that the server is being closed.
		Mockito.verify(super.getServerState().getServer(), Mockito.times(1)).close();
	}

	@Override
	public void testOnDeactivate() { }
}
