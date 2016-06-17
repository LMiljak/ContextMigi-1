package com.github.migi_1.Context.model.entity;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.migi_1.Context.server.ServerWrapper;
import com.github.migi_1.ContextMessages.PlatformPosition;
import com.github.migi_1.ContextMessages.PositionMessage;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

/**
 * Assigns carriers to a position under a platform when a connection has been
 * established with a client.
 */
public class CarrierAssigner implements ConnectionListener {

    private HashMap<PlatformPosition, HostedConnection> carrierAddressMap = new HashMap<>(4);

    /**
     * Constructor for CarrierAssigner.
     *
     * @param server
     *            The server to which the clients can connect.
     */
    public CarrierAssigner(ServerWrapper server) {
        server.getServer().addConnectionListener(this);
    }

    /**
     * Called when a connection has been established with the server.
     *
     * Assigns the client that connected a position under the platform and also
     * places a Carrier under the platform on the created position.
     */
    @Override
    public void connectionAdded(Server server, HostedConnection conn) {
        for (PlatformPosition position : PlatformPosition.values()) {
            if (carrierAddressMap.get(position) == null) {
                carrierAddressMap.put(position, conn);
                conn.send(new PositionMessage(position));
                Logger.getGlobal().log(Level.INFO, "Given position " + position
                        + " to " + conn.getAddress());
                break;
            }
        }
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        Logger.getGlobal().log(Level.INFO,
                conn.getAddress() + " has disconnected");
        for (PlatformPosition position : PlatformPosition.values()) {
            carrierAddressMap.remove(position, conn);
        }
    }

    /**
     * Gets the ip address of a carrier.
     *
     * @param carrierPosition
     *            The position of the carrier to get the ip address of.
     * @return The found ip address. Empty String if there is no address on that
     *         position.
     */
    public String getAddress(PlatformPosition carrierPosition) {
        HostedConnection conn = carrierAddressMap.get(carrierPosition);
        if (conn == null) {
            return "";
        } else {
            return conn.getAddress();
        }
    }

    /**
     * Setter for the carrierAdressMap.
     *
     * @param newMap
     *            the new carrier adress map.
     */
    public void setCarrierAdressMap(HashMap<PlatformPosition, HostedConnection> newMap) {
        carrierAddressMap = newMap;
    }
}
