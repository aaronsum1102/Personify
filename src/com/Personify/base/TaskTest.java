package com.Personify.base;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.integration.Messenger;
import com.Personify.integration.TaskInfo;

class TaskTest {
	private Task task;
	private TaskInfo taskInfo;
	private Motivation motivationQuotes;
	private Messenger messages;
	private String taskName;
	private String dueDate;
	private String status;
	private String priority;
	
	
	@BeforeEach
	void setUp() throws Exception {
		motivationQuotes = new Motivation();
		messages = new Messenger();
		taskName = "Doing grocery";
		dueDate =  "2018-03-10";
		status = "to do";
		priority = "high";
		taskInfo = new TaskInfo(taskName, dueDate, status, priority);
		task = new Task(taskInfo, messages, motivationQuotes);
	}

	@AfterEach
	void tearDown() throws Exception {
		task = null;
	}
	
	@Test
	void testNameIsNotEmptyString() {
		String name = "Doing shopping for Christmas";
		boolean result =Task.isNameNotEmptyString(name);
		assertEquals(true, result);
	}
	
	@Test
	void testNameIsEmptyString() {
		String name = "";
		boolean result = Task.isNameNotEmptyString(name);
		assertEquals(true, !result);
	}

	@Test
	void testChangeNameWithNewName() {
		String newName = "Doing shopping for Christmas";
		boolean result = task.changeName(newName);
		assertEquals(true, result);
		assertEquals(newName, task.getName());
	}
	
	@Test
	void testChangeNameWithRepeatedName() {
		String newName = taskName;
		boolean result = task.changeName(newName);
		assertEquals(false, result);
	}
	
	@Test
	void testChangeNameWithRepeatedNameButPartiallyUpperCase() {
		String newName = "DOING grocery";
		boolean result = task.changeName(newName);
		assertEquals(false, result);
		assertEquals(taskName, task.getName());
	}
	
	@Test
	void testChangeNameWithRepeatedNameButLowerCase() {
		String newName = "doing grocery";
		boolean result = task.changeName(newName);
		assertEquals(false, result);
		assertEquals(taskName, task.getName());
	}
	
	@Test
	void testInitialisationOfDueDate() {
		assertEquals(LocalDate.parse(dueDate), task.getDueDate());
	}
	
	@Test
	void testInitialisationOfDueDateWithoutGivenADate() {
		taskInfo = new TaskInfo(taskName, "", status, priority);
		task = new Task(taskInfo, messages, motivationQuotes);
		assertEquals(LocalDate.now(), task.getDueDate());
	}
	
	@Test
	void testInitialisationOfDueDateWithIncorrectFormat() {
		taskInfo = new TaskInfo(taskName, "03-02-2018", status, priority);
		task = new Task(taskInfo, messages, motivationQuotes);
		assertEquals(LocalDate.now(), task.getDueDate());
	}
	
	@Test
	void testChangeNameWithEmptyString() {
		String newName = "";
		boolean result = task.changeName(newName);
		assertEquals(false, result);
		assertEquals(taskName, task.getName());
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
		assertEquals(LocalDate.parse(dueDate), task.getDueDate());
	}
	
	@Test
	void testChangeDueDateWithInvalidDateFormat() {
		String newDueDate = "2018-3-10";
		boolean result = task.changeDueDate(newDueDate);
		assertEquals(false, result);
		assertEquals(LocalDate.parse(dueDate), task.getDueDate());
	}
	
	@Test
	void testChangeDueDateWithoutADate() {
		String newDueDate = "";
		boolean result = task.changeDueDate(newDueDate);
		assertEquals(false, result);
		assertEquals(LocalDate.parse(dueDate), task.getDueDate());
	}
	
	@Test
	void testSetPriority() {
		String newPriority = "medium";
		task.setPriority(newPriority);
		assertEquals(newPriority, task.getPrioirtyObject().getPriority());
	}
	
	@Test
	void testSetStatus() {
		String newStatus = "done";
		task.setStatus(newStatus);
		assertEquals(newStatus, task.getStatusObject().getStatus());
	}
}
