package com.Personify.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MessagesTest {
	@Test
	void testAddMessage() {
		Messages messages = new Messages();
		boolean result = messages.addMessage("Falcon Heavy rocks!");
		assertEquals(true, result);
	}
}
