package com.Personify.base;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.integration.Messenger;

class StatusTest {
	private Status status;
	private Messenger messages = new Messenger();
	private static final String DEFAULT_STATUS = "to do";

	@BeforeEach
	void setup() throws Exception {
		status = new Status(messages, "to do");
	}
	
	@AfterEach
	void tearDown() {
		status = null;
	}
	
	@Test
	void testInstantiationWithOneOfTheValidStatusInLowerCase() {
		String newStatus = "in progress";
		status = new Status(messages, newStatus);
		String initialStatus = status.getStatus();
		assertEquals(newStatus, initialStatus);
	}
	
	@Test
	void testInstantiationWithOneOfTheValidStatusInUpperCase() {
		String newStatus = "IN PROGRESS";
		status = new Status(messages, newStatus);
		String initialStatus = status.getStatus();
		assertEquals(newStatus.toLowerCase(), initialStatus);
	}
	
	@Test
	void testInstantiationWithOneOfTheValidStatusInMixedCase() {
		String newStatus = "In PrOgReSs";
		status = new Status(messages, newStatus);
		String initialStatus = status.getStatus();
		assertEquals(newStatus.toLowerCase(), initialStatus);
	}
	
	@Test
	void testInstantiationWithEmptyString() {
		status = new Status(messages, "");
		String initialStatus = status.getStatus();
		assertEquals(DEFAULT_STATUS, initialStatus);
	}
	
	@Test
	void testInstantiationWithNumberWrapInString() {
		status = new Status(messages, "124");
		String initialStatus = status.getStatus();
		assertEquals(DEFAULT_STATUS, initialStatus);
	}
	
	@Test
	void testSetStatusWithNewValidStatusInLowerCase() {
		String newStatus = "done";
		boolean result = status.setStatus(newStatus);
		assertEquals(true, result);
		assertEquals(newStatus, status.getStatus());
	}
	
	@Test
	void testSetStatusWitStatusOutOfValidStatusList() {
		String newStatus = "To Dod";
		boolean result = status.setStatus(newStatus);
		assertEquals(false, result);
		assertEquals(DEFAULT_STATUS, status.getStatus());
	}
	
	@Test
	void testSetStatusWithValidStatusInUpperCase() {
		String newStatus = "DONE";
		boolean result = status.setStatus(newStatus);
		assertEquals(true, result);
		assertEquals(newStatus.toLowerCase(), status.getStatus());
	}
	
	@Test
	void testSetStatusWithValidSatusInMixedCase() {
		String newStatus = "DoNe";
		boolean result = status.setStatus(newStatus);
		assertEquals(true, result);
		assertEquals(newStatus.toLowerCase(), status.getStatus());
	}
	
	@Test
	void testSetStatusWithRepeatedStatus() {
		String newStatus = "To do";
		boolean result = status.setStatus(newStatus);
		assertEquals(false, result);
		assertEquals(DEFAULT_STATUS, status.getStatus());
	}
	
	@Test
	void testSetStatusWithEmptyString() {
		boolean result = status.setStatus("");
		assertEquals(false, result);
		assertEquals(DEFAULT_STATUS, status.getStatus());
	}
	
	@Test
	void testSetStatusWithNumberWrapInString() {
		boolean result = status.setStatus("6987352");
		assertEquals(false, result);
		assertEquals(DEFAULT_STATUS, status.getStatus());
	}
}
