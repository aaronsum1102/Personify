package com.Personify.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessengerTest {
	private Messenger messenger;
	
	@BeforeEach
	void setup() {
		messenger = new Messenger();
	}
	
	@AfterEach
	void tearDown() {
		messenger = null;
	}
	
	@Test
	void testAddMessage() {
		boolean result = messenger.addMessage("Falcon Heavy rocks!");
		assertEquals(true, result);
	}
	
	@Test
	void testAddMessageWithEmptyString() {
		boolean result = messenger.addMessage("");
		assertEquals(false, result);
	}
	
	@Test
	void testClearAllMessages() {
		assertEquals(true, messenger.addMessage("This is a test case."));
		assertEquals(true, messenger.addMessage("I'm a test."));
		assertEquals(true, messenger.addMessage("I'm a test as well."));
		assertEquals(3, messenger.getMessages().size());
		messenger.clearMessages();
		assertEquals(0, messenger.getMessages().size());
	}
}
