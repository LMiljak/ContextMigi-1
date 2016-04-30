package com.github.migi_1.context.networking.serverside;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Used on the server side to help devices on the LAN
 * to find the server.
 * For more information see 
 * http://michieldemey.be/blog/network-discovery-using-udp-broadcast/
 * This code is highly based on that tutorial.
 * 
 * SHINGLETON class.
 */
public class ClientFinder {

	/** The shingleton instance of this class. */
	private static final ClientFinder INSTANCE = new ClientFinder();
	
	/** The password used to validate whether a received package is from an user of this game. */
	private static final String PASSWORD = "yo man, kan ik joinen?";
	
	/**
	 * Gets the instance of this shingleton class.
	 * 
	 * @return
	 * 		The shingleton instance of this class.
	 */
	public static ClientFinder getInstance() {
		return INSTANCE;
	}
	
	/** Private empty constructor so it can't be instantiated (shingleton class property). */
	private ClientFinder() { }
	
	/**
	 * Does the following things in order.
	 * 1. Opens a socket using the port of the Host that can listen to UDP requests.
	 * 2. Waits for a packet to be received.
	 * 3. Checks whether that packet is valid (from a user of this game).
	 * 4. If the check passed, sends a response back.
	 * 5. Repeats from 2.
	 * 
	 * Make sure to call this method on a new Thread, otherwise the code will be caught
	 * in an infinite loop.
	 * 
	 * @throws IOException
	 */
	public void handleMessages() throws IOException {
		DatagramSocket socket = new DatagramSocket(Host.getInstance().getPort(), InetAddress.getByName("0.0.0.0"));
		socket.setBroadcast(true);
		
		while (true) {
			DatagramPacket packet = waitForPacket(socket);
			
			if (checkReceivedPacket(packet)) {
				byte[] responseData = "Sure thing bro".getBytes();
				DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
				socket.send(responsePacket);
			}
		}
	}
	
	/**
	 * Waits for a packet to arrive in a socket.
	 * 
	 * @param socket
	 * 		The socket we're waiting the package to arrive in.
	 * @return
	 * 		The received packet.
	 * @throws IOException
	 */
	private DatagramPacket waitForPacket(DatagramSocket socket) throws IOException {
		final int bufferSize = 15000; //The maximum size of the packet.
		DatagramPacket packet = new DatagramPacket(new byte[bufferSize], bufferSize);
		
		//Wait for something to send a packet and receive it in the empty one we just created.
		socket.receive(packet);
		
		//Return the received packet.
		return packet;
	}
	
	/**
	 * Checks whether or not a received packet is valid (from an actual client
	 * playing this game and not some random loose packet).
	 * 
	 * @param packet
	 * 		The packet received by the socket.
	 * @return
	 * 		True iff the packet passed the check and is deemed a player of this game.
	 */
	private boolean checkReceivedPacket(DatagramPacket packet) {
		//The data in the packet is still in byte form.
		//The String class can convert this data into a String using a simple constructor.
		String password = new String(packet.getData()).trim();
		
		return password.equals(PASSWORD);
	}
}