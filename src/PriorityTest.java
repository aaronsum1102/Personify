import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PriorityTest {
	Priority p;
	
	@BeforeEach
	void setUp() throws Exception {
		p = new Priority();
	}
	
	@Test
	void testIsValidPriorityWithValidPriority() {
		String priority = "medium";
		boolean result = p.isValidPriority(priority);
		assertEquals(true, result);
	}
	
	@Test
	void testIsValidPriorityWithValidUpperCasePriority() {
		String priority = "HIGH";
		boolean result = p.isValidPriority(priority);
		assertEquals(true, result);
	}
	
	@Test
	void testIsValidPriorityWithInvalidPriority() {
		String priority = "me@dium";
		boolean result = p.isValidPriority(priority);
		assertEquals(false, result);
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
}
