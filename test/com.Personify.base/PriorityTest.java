
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.base.Priority;
import com.Personify.integration.Messenger;

class PriorityTest {
	private Priority priority;
	private Messenger messages = new Messenger();
	private String initialPriority;
	private static final String DEFAULT_PRIORITY = "high";
	
	@BeforeEach
	void setUp() throws Exception {
		initialPriority = "low";
		priority = new Priority(messages, initialPriority);
	}
	
	@AfterEach
	void tearDown() {
		priority = null;
	}
	
	@Test
	void testInstantiationOfPriorityObject() {
		assertEquals(initialPriority, priority.getPriority());
	}
	
	@Test
	void testInstantiationOfPriorityObjectWithPriorityInCapitalCase() {
		initialPriority = "LOW";
		priority = new Priority(messages, initialPriority);
		assertEquals(initialPriority.toLowerCase(), priority.getPriority());
	}
	
	@Test
	void testInstantiationOfPriorityObjectWithEmptyString() {
		initialPriority = "";
		priority = new Priority(messages, initialPriority);
		assertEquals(DEFAULT_PRIORITY, priority.getPriority());
	}
	
	@Test
	void testInstantiationOfPriorityObjectWithNumberWrapInString() {
		initialPriority = "214";
		priority = new Priority(messages, initialPriority);
		assertEquals(DEFAULT_PRIORITY, priority.getPriority());
	}
	
	@Test
	void testSetPriorityWithNewValueInPriorityCollection() {
		String newPriority = "Medium";
		boolean result = priority.setPriority(newPriority);
		assertEquals(true, result);
		assertEquals(newPriority.toLowerCase(), priority.getPriority());
	}
	
	@Test
	void testSetPriorityWithValueNotInValidPriorityCollection() {
		String newPriority = "Lowa";
		boolean result = priority.setPriority(newPriority);
		assertEquals(false, result);
		assertEquals(initialPriority, priority.getPriority());
	}
	
	@Test
	void testSetPriorityWithEmptyString() {
		String newPriority = "";
		boolean result = priority.setPriority(newPriority);
		assertEquals(false, result);
		assertEquals(initialPriority, priority.getPriority());
	}
	
	@Test
	void testSetPriorityWithNumberWrapInString() {
		String newPriority = "124";
		boolean result = priority.setPriority(newPriority);
		assertEquals(false, result);
		assertEquals(initialPriority, priority.getPriority());
	}
	
	@Test
	void testSetPriorityWithSamePriority() {
		String newPriority = initialPriority;
		boolean result = priority.setPriority(newPriority);
		assertEquals(false, result);
		assertEquals(initialPriority, priority.getPriority());
	}
	
	@Test
	void testSetPriorityWithNewPriorityInLowerCase() {
		String priorityToCheck = "medium";
		boolean result = priority.setPriority(priorityToCheck);
		assertEquals(true, result);
		assertEquals(priorityToCheck, priority.getPriority());
	}
	
	@Test
	void testSetPriorityWithNewPriorityInUpperCase() {
		String priorityToCheck = "MEDIUM";
		boolean result = priority.setPriority(priorityToCheck);
		assertEquals(true, result);
		assertEquals(priorityToCheck.toLowerCase(), priority.getPriority());
	}
	
	@Test
	void testSetPriorityWithNewPriorityInCamelCase() {
		String priorityToCheck = "mEdiUm";
		boolean result = priority.setPriority(priorityToCheck);
		assertEquals(true, result);
		assertEquals(priorityToCheck.toLowerCase(), priority.getPriority());
	}
	
}
