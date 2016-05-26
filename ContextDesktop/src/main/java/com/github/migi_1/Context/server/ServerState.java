package com.github.migi_1.Context.server;

import com.jme3.network.Server;

/**
 * An abstract Server state for the server.
 */
public abstract class ServerState {
	
	private Server server;
	
	/**
	 * Constructor for ServerState.
	 * 
	 * @param server
	 * 		The server that this state represents.
	 */
	public ServerState(Server server) {
		this.server = server;
	}
	
	/**
	 * Gets the server on which this state applies.
	 * 
	 * @return
	 * 		The server on which this state applies.
	 */
	protected Server getServer() {
		return server;
	}
	
	/**
	 * Called when the state of the ServerWrapper changes to this state.
	 */
	public abstract void onActivate();
	
	/**
	 * Called when the state of the ServerWrapper changes from this state.
	 * This can be used to for example, do some cleanup.
	 */
	public abstract void onDeactivate();

	@Override
	public int hashCode() {
		return server.hashCode() + getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ServerState other = (ServerState) obj;
		
		return server.equals(other.server);
	}
}
