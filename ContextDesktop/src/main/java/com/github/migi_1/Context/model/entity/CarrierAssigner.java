package com.github.migi_1.Context.model.entity;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.PositionMessage;
import com.github.migi_1.Context.model.MainEnvironment;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

/**
 * Assigns carriers to a position under a platform when
 * a connection has been established with a client.
 */
public class CarrierAssigner implements ConnectionListener {

	private Platform platform;
	private HashMap<String, Carrier> addressCarrierMap = new HashMap<>(4);
        private MainEnvironment env;
	
	/**
	 * Constructor for CarrierAssigner.
	 * 
	 * @param platform
	 * 		The platform to which the carriers should be assigned to.
	 * @param server
	 * 		The server to which the clients can connect.
	 * @param env
	 * 		The environment of the game.
	 */
	public CarrierAssigner(Platform platform, ServerWrapper server, MainEnvironment env) {
		this.platform = platform;
                this.env = env;
		server.getServer().addConnectionListener(this);
		System.out.println("hi");
	}

	/**
	 * Called when a connection has been established with the server.
	 * 
	 * Assigns the client that connected a position under the platform
	 * and also places a Carrier under the platform on the created position.
	 */
	@Override
	public void connectionAdded(Server server, HostedConnection conn) {
		System.out.println("hi");
		for (PlatformPosition position : PlatformPosition.values()) {
			if (addressCarrierMap.get(position) == null) {
				Carrier carrier = new Carrier(platform.getModel().getLocalTranslation(), position, env);
				
				addressCarrierMap.put(conn.getAddress(), carrier);
				platform.addCarrier(carrier);
				
				conn.send(new PositionMessage(position));
				
				Logger.getGlobal().log(Level.INFO, "Given position " + position + " to " + conn.getAddress());
				
				break;
			}
		}
	}

	@Override
	public void connectionRemoved(Server server, HostedConnection conn) {
		addressCarrierMap.remove(conn.getAddress());
	}
	
}
