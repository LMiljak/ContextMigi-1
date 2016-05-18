package com.github.migi_1.ContextMessages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.Message;

/**
 * This class listens to specific messages sent from a server/client.
 * It differs from com.jme3.network.MessageListener by that it can
 * only listen to one specific message type, so that there don't have
 * to be many instanceof statements in the messageReceived method.
 * 
 * @param <T>
 * 		The message type this class should handle.
 */
@SuppressWarnings("rawtypes")
public interface MessageListener<T extends AbstractMessage> extends com.jme3.network.MessageListener {

	/** 
	 * Called when any message has been received.
	 * Checks whether the message is the same type of message
	 * that this class listens to. If yes, then the other
	 * messageReceived method is called.
	 * 
	 * @param source
	 * 		The client/server that sent this message.
	 * @param message
	 * 		The message sent.
	 */
	@SuppressWarnings("unchecked")
	@Override
	default void messageReceived(Object source, Message message) {
		if (message.getClass().equals(getMessageClass())) {
			messageReceived(source, (T) message);
		}
	}

	/**
	 * Called when a message of the type that this class should
	 * handle is received.
	 * 
	 * @param source
	 * 		The client/server that sent this message.
	 * @param message
	 * 		The message sent.
	 */
	void messageReceived(Object source, T message);
	
	/**
	 * Should return the Class of the messages that this MessageListener
	 * should handle.
	 * 
	 * For example, if there is a message StringMessage extends AbstractMessage
	 * and this class is instance of MessageListener<StringMessage>, 
	 * then this class should return StringMessage.class.
	 * If it does not do so, bad things may happen.
	 * 
	 * @return
	 * 		The class of the messages that this MessageListener should
	 * 		handle.
	 */
	Class<T> getMessageClass();
}
