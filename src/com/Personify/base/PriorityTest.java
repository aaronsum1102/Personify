package com.Personify.base;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.integration.Messages;

class PriorityTest {
	Priority p;
	Messages messages = new Messages();
	
	@BeforeEach
	void setUp() throws Exception {
		p = new Priority(messages, "high");
	}

	@Test
	void testSetPriorityWithValidPriority() {
		String priority = "Low";
		boolean result = p.setPriority(priority);
		assertEquals(true, result);
		assertEquals("low", p.getPriority());
	}
	
	@Test
	void testSetPriorityWithInvalidPriority() {
		String priority = "Lowa";
		boolean result = p.setPriority(priority);
		assertEquals(false, result);
		assertEquals("high", p.getPriority());
	}
	
	@Test
	void testSetPriorityWithEmptyPriority() {
		String priority = "";
		boolean result = p.setPriority(priority);
		assertEquals(false, result);
		assertEquals("high", p.getPriority());
	}
	
	@Test
	void testSetPriorityWithRepeatedPriority() {
		String priority = "HIGH";
		boolean result = p.setPriority(priority);
		assertEquals(false, result);
		assertEquals("high", p.getPriority());
	}
	
	@Test
	void testIsRepeatedPriorityWithLowerCase() {
		String priority = "medium";
		boolean result = p.setPriority(priority);
		assertEquals(true, result);
		assertEquals("medium", p.getPriority());
	}
	
	@Test
	void testIsRepeatedPriorityWithUpperCase() {
		String priority = "MEDIUM";
		boolean result = p.setPriority(priority);
		assertEquals(true, result);
		assertEquals("medium", p.getPriority());
	}
	
	@Test
	void testIsRepeatedPriorityWithMixedCase() {
		String priority = "MeDiUm";
		boolean result = p.setPriority(priority);
		assertEquals(true, result);
		assertEquals("medium", p.getPriority());
	}
	
}
