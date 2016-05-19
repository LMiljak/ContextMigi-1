package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.jme3.network.AbstractMessage;
import com.jme3.network.Message;

/**
 * Test suite for the (default methods of the) MessageListener interface.
 */
public class TestMessageListener {

	/** Message class used for testing. This message class is being handled.*/
	private class TestMessage1 extends AbstractMessage { }
	/** Message class used for testing. This message class isn't being handled. */
	private class TestMessage2 extends AbstractMessage { }
	
	private MessageListener<TestMessage1> listener;
	
	private Object receivedSource;
	private Message receivedMessage;
	
	/**
	 * Called before every test case.
	 * Initialises the listener field with a MessageListener
	 * that handles the TestMessage message.
	 */
	@Before
	public void setUp() {
		listener = new MessageListener<TestMessage1>() {
			@Override
			public void messageReceived(Object source, TestMessage1 message) {
				receivedSource = source;
				receivedMessage = message;
			}

			@Override
			public Class<TestMessage1> getMessageClass() {
				return TestMessage1.class;
			}
		};
		
		receivedSource = null;
		receivedMessage = null;
	}
	
	/**
	 * Tests the default messageReceived method by checking that
	 * the other messageReceived method is called if the message
	 * is the correct type.
	 */
	@Test
	public void testMessageReceivedCorrectMessage() {
		Object source = new Object();
		Message message = new TestMessage1();
		
		listener.messageReceived(source, message);
		
		assertEquals(source, receivedSource);
		assertEquals(message, receivedMessage);
	}
	
	/**
	 * Tests the default messageReceived method by checking that
	 * the other messageReceived method is NOT called if the 
	 * message is not the correct type.
	 */
	@Test
	public void testMessageReceivedInCorrectMessage() {
		Object source = new Object();
		Message message = new TestMessage2(); //A message this handler shouldn't handle
		
		listener.messageReceived(source, message);
		
		assertNull(receivedSource);
		assertNull(receivedMessage);
	}
}
