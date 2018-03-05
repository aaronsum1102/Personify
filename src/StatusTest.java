import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatusTest {
	Status s;

	@BeforeEach
	void setup() throws Exception {
		s = new Status();
	}

	@Test
	void testIsValidStatusWithCapitalCase() {
		assertEquals(true, s.isValidStatus("TO DO"));
	}

	@Test
	void testIsValidStatusWithMixedCase() {
		assertEquals(true, s.isValidStatus("iN pRoGreSS"));
	}

	@Test
	void testIsValidStatusWithInvalidCase() {
		assertEquals(false, s.isValidStatus("@iN pRoGreSS"));
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
	void testSetStatusWithEmptyStatus() {
		boolean result = s.setStatus("");
		assertEquals(false, result);
		assertEquals("to do", s.getStatus());
	}
}
