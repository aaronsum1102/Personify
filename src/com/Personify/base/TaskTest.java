package com.Personify.base;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.integration.Messages;
import com.Personify.integration.TaskInfo;

class TaskTest {
	private Task task;
	private TaskInfo taskInfo;
	private Motivation motivationQuotes;
	private Messages messages;
	
	
	@BeforeEach
	void setUp() throws Exception {
		motivationQuotes = new Motivation();
		messages = new Messages();
		taskInfo = new TaskInfo("Doing grocery", "2018-03-10", "to do", "high");
		task = new Task(taskInfo, messages, motivationQuotes);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testIsNameNotEmptyStringWithNonEmptyString() {
		String name = "Doing shopping for Christmas";
		boolean result =Task.isNameNotEmptyString(name);
		assertEquals(true, result);
	}
	
	@Test
	void testIsNameNotEmptyStringWithEmptyString() {
		String name = "";
		boolean result = Task.isNameNotEmptyString(name);
		assertEquals(false, result);
	}

	@Test
	void testChangeNameWithDifferentName() {
		String newName = "Doing shopping for Christmas";
		boolean result = task.changeName(newName);
		assertEquals(true, result);
		assertEquals(newName, task.getName());
	}
	
	@Test
	void testChangeNameWithSameNameAndPartiallyUpperCase() {
		String newName = "DOING grocery";
		boolean result = task.changeName(newName);
		assertEquals(false, result);
		assertEquals("Doing grocery", task.getName());
	}
	
	@Test
	void testChangeNameWithSameNameAndLowerCase() {
		String newName = "doing grocery";
		boolean result = task.changeName(newName);
		assertEquals(false, result);
		assertEquals("Doing grocery", task.getName());
	}
	
	@Test
	void testChangeNameWithEmptyString() {
		String newName = "";
		boolean result = task.changeName(newName);
		assertEquals(false, result);
		assertEquals("Doing grocery", task.getName());
	}

	@Test
	void testChangeDueDateWithValidDateFormat() {
		String newDueDate = "2018-04-04";
		boolean result = task.changeDueDate(newDueDate);
		assertEquals(true, result);
		assertEquals(LocalDate.parse(newDueDate), task.getDueDate());
	}
	
	@Test
	void testChangeDueDateWithSameDate() {
		String newDueDate = "2018-03-10";
		boolean result = task.changeDueDate(newDueDate);
		assertEquals(false, result);
		assertEquals(LocalDate.parse("2018-03-10"), task.getDueDate());
	}
	
	@Test
	void testChangeDueDateWithInvalidDateFormat() {
		String newDueDate = "2018-3-10";
		boolean result = task.changeDueDate(newDueDate);
		assertEquals(false, result);
		assertEquals(LocalDate.parse("2018-03-10"), task.getDueDate());
	}
}
