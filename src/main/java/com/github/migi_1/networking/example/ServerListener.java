package com.github.migi_1.networking.example;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

public class ServerListener implements MessageListener<HostedConnection> {

	public void messageReceived(HostedConnection source, Message m) {
		if (m instanceof PrintMessage) {
			PrintMessage message = (PrintMessage) m;
			System.out.println(message.getToPrint());
		}
	}

}
