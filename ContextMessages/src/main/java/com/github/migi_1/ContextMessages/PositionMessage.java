package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Message for sending the client their Position.
 */
@Serializable
public class PositionMessage extends AbstractMessage {

	private PlatformPosition position;
	
	/** Public empty constructor used by the networking library. */
	public PositionMessage() { }
	
	/**
	 * Constructor for PositionMessage.
	 * 
	 * @param position
	 * 		The position to send.
	 */
	public PositionMessage(PlatformPosition position) {
		this.position = position;
	}
	
	/**
	 * Gets the position stored in this message.
	 * 
	 * @return
	 * 		The position stored in this message.
	 */
	public PlatformPosition getPosition() {
		return position;
	}
}
