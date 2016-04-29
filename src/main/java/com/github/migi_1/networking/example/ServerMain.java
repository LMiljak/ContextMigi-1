package com.github.migi_1.networking.example;

import java.io.IOException;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;

public class ServerMain extends SimpleApplication {

	public static void main(String[] args) {
		ServerMain app = new ServerMain();
		app.start(JmeContext.Type.Headless);
	}
	
	@Override
	public void simpleInitApp() {
		try {
			Server myServer = Network.createServer(1234);
			myServer.start();
			
			while (true) {
				if (!myServer.getConnections().isEmpty()) {
					System.out.println("SOMEBODY CONNECTED! OMG!");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
