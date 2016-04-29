package com.github.migi_1.context.networking.example;

import java.io.IOException;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.JmeContext;

public class ClientMain extends SimpleApplication {

	public static void main(String[] args) {
		ClientMain app = new ClientMain();
		app.start(JmeContext.Type.Display);
		Serializer.registerClass(PrintMessage.class);
	}

	@Override
	public void simpleInitApp() {
		try {
			Client myClient = Network.connectToServer("localhost", 1234);
			myClient.start();
			
			myClient.addMessageListener(new ClientListener(), PrintMessage.class);
			
			Message message = new PrintMessage("I luv u so much");
			myClient.send(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
