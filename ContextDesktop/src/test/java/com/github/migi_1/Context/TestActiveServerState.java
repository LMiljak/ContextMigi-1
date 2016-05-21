package com.github.migi_1.Context;

import org.mockito.Mockito;

import com.jme3.network.Server;

/**
 * Test suite for the ActiveServerState class.
 */
public class TestActiveServerState extends TestServerState {

	@Override
	public void setUp() {
		Server server = Mockito.mock(Server.class);
		
		super.setServerState(new ActiveServerState(server), server);
	}

	@Override
	public void testOnActivate() {
		
	}

	@Override
	public void testOnDeactivate() {
		// TODO Auto-generated method stub

	}

}
