package com.github.migi_1.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	/** The password used to validate whether a received package is from a client of this game. */
	private static final String PASSWORD = "yo man, kan ik joinen?";

	/** The port to which clients should try and connect. */
	private static final int PORT = 4269;

	private DatagramSocket socket;

	private boolean running;

	/**
	 * Launches the ClientFinder. Upon pressing the enter key in the console
	 * the ClientFinder terminates. This main method is mainly used for testing
	 * purposes only and should be removed for the final version.
	 *
	 * @param args
	 * 		ignored.
	 */
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10);

		INSTANCE.findClients(es);
		es.execute(() -> {
			(new Scanner(System.in)).nextLine();
			INSTANCE.stop();
			System.out.println("Stopping ClientFinder");
		});
		es.shutdown();
	}

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
	 * Allows the findServer method from the ServerFinder class to find this server.
	 *
	 * It executes the following tasks:
	 * 1. Opens a socket that can listen to UDP requests.
	 * 2. Waits for a packet to be received.
	 * 3. Checks whether that packet is valid (from a user of this game).
	 * 4. If the check passed, sends a response back.
	 * 5. Repeats from 2.
	 *
	 * @param executorService
	 * 		The executorService used to run this task on a new Thread.
	 */
	public void findClients(ExecutorService executorService) {
		running = true;
		executorService.execute(() -> findClients());
	}

	/**
	 * Stops the ClientFinder from finding clients. This method only has effect if the findClients method
	 * has been called.
	 */
	public void stop() {
		running = false;
		socket.close();
	}

	/**
	 * Helper method for the other findClients method.
	 */
	private void findClients() {
		try {
			socket = new DatagramSocket(PORT, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);

			while (running) {
				DatagramPacket packet = waitForPacket();

				if (checkReceivedPacket(packet)) {
					byte[] responseData = "Yea dude, Im a real server".getBytes();
					DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
					socket.send(responsePacket);
				}
			}

		} catch (IOException e) { }

	}

	/**
	 * Waits for a packet to arrive in the socket.
	 *
	 * @param socket
	 * 		The socket we're waiting the package to arrive in.
	 * @return
	 * 		The received packet.
	 * @throws IOException
	 */
	private DatagramPacket waitForPacket() throws IOException {
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