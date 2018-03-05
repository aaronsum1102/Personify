import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {
	Task t;
	
	@BeforeEach
	void setUp() throws Exception {
		t = new Task("Doing grocery", "2018-03-10", "TO DO", "HIGH");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testChangeNameWithDifferentName() {
		String newName = "Doing shopping for Christmas";
		boolean result = t.changeName(newName);
		assertEquals(true, result);
		assertEquals(newName, t.getName());
	}
	
	@Test
	void testChangeNameWithSameNameAndPartiallyUpperCase() {
		String newName = "DOING grocery";
		boolean result = t.changeName(newName);
		assertEquals(false, result);
		assertEquals("Doing grocery", t.getName());
	}
	
	@Test
	void testChangeNameWithSameNameAndPartiallyLowerCase() {
		String newName = "doing grocery";
		boolean result = t.changeName(newName);
		assertEquals(false, result);
		assertEquals("Doing grocery", t.getName());
	}
	
	@Test
	void testChangeNameWithEmptyString() {
		String newName = "";
		boolean result = t.changeName(newName);
		assertEquals(false, result);
		assertEquals("Doing grocery", t.getName());
	}

	@Test
	void testChangeDueDateWithValidDate() {
		String newDueDate = "2018-04-04";
		boolean result = t.changeDueDate(newDueDate);
		assertEquals(true, result);
		assertEquals(LocalDate.parse(newDueDate), t.getDueDate());
	}
	
	@Test
	void testChangeDueDateWithSameDate() {
		String newDueDate = "2018-03-10";
		boolean result = t.changeDueDate(newDueDate);
		assertEquals(false, result);
		assertEquals(LocalDate.parse("2018-03-10"), t.getDueDate());
	}
	
	@Test
	void testChangeDueDateWithInvalidDate() {
		String newDueDate = "2018-3-10";
		boolean result = t.changeDueDate(newDueDate);
		assertEquals(false, result);
		assertEquals(LocalDate.parse("2018-03-10"), t.getDueDate());
	}

}
