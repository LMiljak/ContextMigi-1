package com.github.migi_1.Context.model.entity;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.migi_1.Context.ServerWrapper;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.PositionMessage;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

public class CarrierAssigner implements ConnectionListener {

	private Platform platform;
	private HashMap<String, Carrier> addressCarrierMap = new HashMap<>(4);
	
	public CarrierAssigner(Platform platform, ServerWrapper server) {
		this.platform = platform;
		server.getServer().addConnectionListener(this);
	}

	@Override
	public void connectionAdded(Server server, HostedConnection conn) {
		for (PlatformPosition position : PlatformPosition.values()) {
			if (addressCarrierMap.get(position) == null) {
				Carrier carrier = new Carrier(platform.getModel().getLocalTranslation(), position);
				
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
