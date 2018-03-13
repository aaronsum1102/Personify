package com.Personify.base;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.integration.Messages;

class StatusTest {
	Status s;
	Messages messages = new Messages();

	@BeforeEach
	void setup() throws Exception {
		s = new Status(messages, "to do");
	}

	@Test
	void testSetStatusWithValidStatus() {
		boolean result = s.setStatus("In progress");
		assertEquals(true, result);
		assertEquals("in progress", s.getStatus());
	}

	@Test
	void testSetStatusWithInvalidStatus() {
		boolean result = s.setStatus("To Dod");
		assertEquals(false, result);
		assertEquals("to do", s.getStatus());
	}
	
	@Test
	void testSetStatusWithUpperCaseStatus() {
		boolean result = s.setStatus("DONE");
		assertEquals(true, result);
		assertEquals("done", s.getStatus());
	}
	
	@Test
	void testSetStatusWithMixedCaseStatus() {
		boolean result = s.setStatus("DoNe");
		assertEquals(true, result);
		assertEquals("done", s.getStatus());
	}
	
	@Test
	void testSetStatusWitLowerCaseStatus() {
		boolean result = s.setStatus("done");
		assertEquals(true, result);
		assertEquals("done", s.getStatus());
	}
	
	@Test
	void testSetStatusWithRepeatedStatus() {
		boolean result = s.setStatus("To do");
		assertEquals(false, result);
		assertEquals("to do", s.getStatus());
	}
	
	@Test
	void testSetStatusWithEmptyStatus() {
		boolean result = s.setStatus("");
		assertEquals(false, result);
		assertEquals("to do", s.getStatus());
	}
}
