package com.github.migi_1.context.networking.example;

import java.io.IOException;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.JmeContext;

public class ServerMain extends SimpleApplication {

	public static void main(String[] args) {
		ServerMain app = new ServerMain();
		app.start(JmeContext.Type.Headless);
		Serializer.registerClass(PrintMessage.class);
	}
	
	@Override
	public void simpleInitApp() {
		try {
			Server myServer = Network.createServer(1234);
			myServer.start();
			
			myServer.addMessageListener(new ServerListener(), PrintMessage.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
