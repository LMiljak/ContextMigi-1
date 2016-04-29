package com.github.migi_1.context.networking.example;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class PrintMessage extends AbstractMessage {
	
	private String toPrint; //custom message data
	
	public PrintMessage() { } //Empty default constructor
	
	public PrintMessage(String toPrint) { //Custom constructor
		this.toPrint = toPrint;
	}
	
	public String getToPrint() {
		return toPrint;
	}
}
