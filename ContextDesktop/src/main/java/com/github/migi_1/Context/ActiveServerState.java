package com.github.migi_1.Context;

import com.jme3.network.Server;

/**
 * A Server state for when the server is active.
 */
public class ActiveServerState extends ServerState {

	/**
	 * Constructor for ActiveServerState.
	 * 
	 * @param server
	 * 		The server that this state represents.
	 */
	public ActiveServerState(Server server) {
		super(server);
	}

	/**
	 * Called when the state of the ServerWrapper changes to this state.
	 * The ActiveServerState starts the server.
	 */
	@Override
	public void onActivate() {
		super.getServer().start();
	}

	/**
	 * Called when the state of the ServerWrapper changes to this state.
	 * The ActiveServerState does nothing when it's deactivated.
	 */
	@Override
	public void onDeactivate() { }
	
}
