package com.github.migi_1.Context.model.entity;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.migi_1.Context.model.MainEnvironment;
import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.PositionMessage;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

/**
 * Assigns carriers to a position under a platform when
 * a connection has been established with a client.
 */
public class CarrierAssigner implements ConnectionListener {

	private Platform platform;
	private MainEnvironment environment;
	private HashMap<PlatformPosition, String> addressCarrierMap = new HashMap<>(4);
	
	/**
	 * Constructor for CarrierAssigner.
	 * 
	 * @param platform
	 * 		The platform to which the carriers should be assigned to.
	 * @param server
	 * 		The server to which the clients can connect.
	 * @param environment
	 * 		The environment in which the carrier should be located.
	 */
	public CarrierAssigner(Platform platform, ServerWrapper server, MainEnvironment environment) {
		this.platform = platform;
		this.environment = environment;
		server.getServer().addConnectionListener(this);
	}

	/**
	 * Called when a connection has been established with the server.
	 * 
	 * Assigns the client that connected a position under the platform
	 * and also places a Carrier under the platform on the created position.
	 */
	@Override
	public void connectionAdded(Server server, HostedConnection conn) {
		for (PlatformPosition position : PlatformPosition.values()) {
			if (addressCarrierMap.get(position) == null) {
				Carrier carrier = new Carrier(platform.getModel().getWorldTranslation(), position, environment);
				
				addressCarrierMap.put(position, conn.getAddress());
				platform.addCarrier(carrier);
				environment.addEntity(carrier);
				
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
	
	/**
	 * Gets the ip address of a carrier.
	 * 
	 * @param carrierPosition
	 * 		The position of the carrier to get the ip address of.
	 * @return
	 * 		The found ip address. Empty String if there is no address on that position.
	 */
	public String getAddress(PlatformPosition carrierPosition) {
		String res = addressCarrierMap.get(carrierPosition);
		if (res == null) {
			return "";
		} else {
			return res;
		}
	}
	
}
