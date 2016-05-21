package com.github.migi_1.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.network.Server;

/**
 * A Server state for when the Server isn't active.
 */
public class InactiveServerState extends ServerState {

	/**
	 * Constructor for InactiveServerState.
	 * 
	 * @param server
	 * 		The server that this state represents.
	 */
	public InactiveServerState(Server server) {
		super(server);
	}

	/**
	 * Called when this state gets activated.
	 * The InactiveServerState closes the server.
	 */
	@Override
	public void onActivate() {
		Logger.getGlobal().log(Level.INFO, "Closing the server.");
		super.getServer().close();
	}

	/**
	 * Called when this state get deactivated.
	 * The InactiveServerState does nothing when this is called.
	 */
	@Override
	public void onDeactivate() { }

}
