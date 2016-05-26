package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class PositionMessage extends AbstractMessage {

	private PlatformPosition position;
	
	public PositionMessage() { }
	
	public PositionMessage(PlatformPosition position) {
		this.position = position;
	}
	
	public PlatformPosition getPosition() {
		return position;
	}
}
