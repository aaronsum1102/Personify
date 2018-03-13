package com.Personify.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.base.Motivation;
import com.Personify.base.Task;

class TaskCollectionTest {
	TaskCollection holder;
	Messages taskMessages = new Messages();
	Motivation motivationalQuotes;
	TaskInfo taskInfo;
	Task task;

	@BeforeEach
	void setUp() throws Exception {
		motivationalQuotes = new Motivation();
		holder = new TaskCollection(taskMessages, motivationalQuotes);
		taskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium");
		task = new Task(taskInfo, taskMessages, motivationalQuotes);
	}

	@Test
	void testAddTaskToCollection() throws IOException, DateTimeParseException {
		boolean result = holder.addTaskToCollection(task);
		assertEquals(true, result);
	}
	
	@Test
	void testAddTaskToCollectionWithEmptryInputs() throws IOException, DateTimeParseException {
		taskInfo = new TaskInfo("", "", "", "");
		Task task = new Task(taskInfo, taskMessages, motivationalQuotes);
		boolean result = holder.addTaskToCollection(task);
		assertEquals(true, result);
	}
	
	@Test
	void testIsNotRepeatedTaskWithNewTask() throws IOException {
		holder.addTaskToCollection(task);
		boolean result = holder.isNotRepeatedTaskInCollection("shopping");
		assertEquals(true, result);
	}
	
	@Test
	void testIsNotRepeatedTaskWithRepeatedTask() throws IOException {
		holder.addTaskToCollection(task);
		boolean result = holder.isNotRepeatedTaskInCollection("laundry");
		assertEquals(false, result);
	}
}
