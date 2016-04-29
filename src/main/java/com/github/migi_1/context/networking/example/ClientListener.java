package com.github.migi_1.context.networking.example;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

public class ClientListener implements MessageListener<Client> {

	public void messageReceived(Client source, Message m) {
		if (m instanceof PrintMessage) {
			PrintMessage message = (PrintMessage) m;
			System.out.println(message.getToPrint());
		}
		
	}

}
