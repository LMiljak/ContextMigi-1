package com.github.migi_1.ContextApp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Used on the client side to find servers on the LAN.
 * For more information see 
 * http://michieldemey.be/blog/network-discovery-using-udp-broadcast/
 * This code is highly based on that tutorial.
 * 
 * SHINGLETON class.
 */
public class ServerFinder {

	/** The shingleton instance of this class. */
	private static final ServerFinder INSTANCE = new ServerFinder(); 
	
	/** The password used to validate */
	private static final String PASSWORD = "Yea dude, Im a real server";
	
	/** The PORT on which the ClientFinder is running. */
	private static final int PORT = 4269;
	
	private static final String IP = "255.255.255.255";
	
	private DatagramSocket socket;
	
	private boolean running;
	
	/**
	 * Launches the ServerFinder. Upon pressing the enter key in the console
	 * the ServerFinder terminates. If a server has been found, it gets printed
	 * to the console. This main method is mainly used for testing purposes only 
	 * and should be removed for the final version.
	 * 
	 * @param args
	 * 		ignored.
	 */
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10);
		
                ServerDiscoveryHandler onFoundServer = new ServerDiscoveryHandler() {
                    @Override
                    public void onServerDiscovery(InetAddress server) {
                        System.out.println("Found a server: " + server.getHostAddress());
                    }
                };
		INSTANCE.findServers(es, onFoundServer);
                
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        (new Scanner(System.in)).nextLine();
			INSTANCE.stop();
			System.out.println("Stopping ServerFinder");
                    }
                });
                
		es.shutdown();
	}
	
	/**
	 * Gets the instance of this shingleton class.
	 * 
	 * @return
	 * 		The shingleton instance of this class.
	 */
	public static ServerFinder getInstance() {
		return INSTANCE;
	}
	
	/** Private empty constructor so it can't be instantiated (shingleton class property). */
	private ServerFinder() { }
	
	/**
	 * Searches for servers on this LAN that are running 
	 * the findClients method on the ClientFinder class.
	 * 
	 * It executes the following tasks:
	 * 1. Opens a socket on the port that the server will be running on.
	 * 2. Repeatedly spams devices on this network, asking if they're
	 * 		a server for this game (this is done on a separate thread). 
	 * 3. Waits for a packet to be received.
	 * 4. Checks whether that packet is from an actual server for this game.
	 * 5. If the check passed, we've successfully found a server and the 
	 * 		onServerDiscovery of the serverDiscoveryHandler gets called.
	 * 6. Repeat from step 3 (step 2 should still be running on a separate thread).
	 * 
	 * @param executorService
	 * 		The executorService used to run this task on a new Thread.
	 * @param serverDiscoveryHandler
	 * 		A ServerDiscoveryHandler which's onServerDiscovery method
	 * 		gets called when a server has been found.		
	 */
	public void findServers(ExecutorService executorService, final ServerDiscoveryHandler serverDiscoveryHandler) {
		running = true;
		
		final int spamPeriod = 5000; //The delay (in ms) between each time the LAN is spammed 
		
		try {
			socket = new DatagramSocket();
			socket.setBroadcast(true);
			
			executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                spamLAN(spamPeriod);
                            }
                        });
                        
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                findServers(serverDiscoveryHandler);
                            }
                        });
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Stops the ServerFinder from finding servers. This method only has effect when
	 * the findServers method has been called.
	 */
	public void stop() {
            if (socket != null) {
                running = false;
                socket.close();
            }
	}
	
	/**
	 * Repeatedly spams devices on this local network, asking if they're
	 * a server for this game (this is done on a separate thread). 
	 * 
	 * @param periodMillis
	 * 		The time in miliseconds between each time packets have been sent to the devices.
	 */
	private void spamLAN(int periodMillis) {
		while (running) {
			try {
				//Sending the password to the localhost, this shouldn't be required in the final version as the host and client
				//probably won't be the same (that would require the Android user to be the host, which is weird). However, this
				//is useful for testing purposes.
				sendPasswordTo(InetAddress.getByName(IP));
				
				//Sending the password to every address on every network we're connected to.
				for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
					if (networkInterface.isLoopback() || !networkInterface.isUp()) {
						continue;
					}
					
					for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
						InetAddress broadcast = interfaceAddress.getBroadcast();
						if (broadcast == null) {
							continue;
						}
						sendPasswordTo(broadcast);
					}
				}
			} catch (IOException e) { 
			    e.printStackTrace();
			}
			
			try {
				Thread.sleep(periodMillis); //Wait a bit before sending packages again, we don't want to spam too much.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends a packet containing the super secret password to the given address.
	 * 
	 * @param address
	 * 		The address to send the password to.
	 */
	private void sendPasswordTo(InetAddress address) {
		final byte[] password = "yo man, kan ik joinen?".getBytes();
		
		try {
			DatagramPacket packet = new DatagramPacket(password, password.length, address, PORT);
			socket.send(packet);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Waits for packets to be received by our socket. If that packet is from a 
	 * server for this game, the onServerDiscovery method gets called in the given
	 * serverDiscoveryHandler.
	 * 
	 * @param serverDiscoveryHandler
	 * 		decides what happens when a server has been found.
	 */
	private void findServers(ServerDiscoveryHandler serverDiscoveryHandler) {
		while (running) {
			try {
				DatagramPacket packet = waitForPacket();
				if (checkReceivedPacket(packet)) {
					//FOUND A SERVER! WOOHOO!
					
					InetAddress serverAddress = packet.getAddress();
					serverDiscoveryHandler.onServerDiscovery(serverAddress);
				}
			} catch (IOException e) { 
			    e.printStackTrace();
			}
		}
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
	 * Checks whether or not a received packet is valid (from an actual server
	 * running this game and not some random loose packet).
	 * 
	 * @param packet
	 * 		The packet received by the socket.
	 * @return
	 * 		True iff the packet passed the check and is deemed a server for this game.
	 */
	private boolean checkReceivedPacket(DatagramPacket packet) {
		//The data in the packet is still in byte form.
		//The String class can convert this data into a String using a simple constructor.
		String password = new String(packet.getData()).trim();
		
		return password.equals(PASSWORD);
	}
}
