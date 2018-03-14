package com.Personify.controller;

import java.io.IOException;
import java.util.List;
import com.Personify.base.Motivation;
import com.Personify.base.Task;
import com.Personify.integration.TaskInfo;
import com.Personify.integration.Messages;
import com.Personify.integration.TaskCollection;

public class Controller {
	private TaskCollection tasks;
	private Messages messages;
	private Motivation motivationalQuotes;

	public Controller() throws IOException {
		messages = new Messages();
		motivationalQuotes = new Motivation();
		tasks = new TaskCollection(messages, motivationalQuotes);
	}

	public Messages getMessages() {
		return messages;
	}

	public TaskCollection getTasks() {
		return tasks;
	}

	public void addTask(final TaskInfo TaskInfo) throws IOException {
		if (isTaskNameValid(TaskInfo.getTaskName())) {
			Task task = new Task(TaskInfo, messages, motivationalQuotes);
			if (tasks.addTaskToCollection(task)) {
				messages.addMessage(String.format("Task added. Total task number: %d.", tasks.getTasks().size()));
				task.getSummary();
			}
		}
	}

	public boolean isTaskNameValid(final String taskName) throws IOException {
		if (isNameNotEmptyString(taskName)) {
			if (isNotRepeatedTask(taskName)) {
				return true;
			} else {
				messages.addMessage("You have the same task in record. Please give me a new name.");
			}
		} else {
			messages.addMessage("I can't understand the task name.");
		}
		return false;
	}

	private boolean isNameNotEmptyString(final String taskName) {
		return Task.isNameNotEmptyString(taskName);
	}

	private boolean isNotRepeatedTask(final String taskName) {
		return tasks.isNotRepeatedTaskInCollection(taskName);
	}

	public List<String> getSystemMessages() {
		return messages.getMessages();
	}

	public List<String> getAllTasksSortedByName() {
		tasks.getTasksSortedByName();
		return messages.getMessages();
	}

	public void clearMessages() {
		messages.clearMessages();
	}

	public boolean isTaskNameValidForEdit(final String taskName) {
		if (isNameNotEmptyString(taskName) && !isNotRepeatedTask(taskName)) {
			return true;
		} else if (!isNotRepeatedTask(taskName)) {
			messages.addMessage(String.format(
					"No task with the name of \"%s\".", taskName));
		} else {
			messages.addMessage("I can't understand the task name.");
		}
		return false;
	}

	public void editTaskName(final String originalName, final String newName) {
		tasks.editTaskName(originalName, newName);
	}
	
	public void editDueDate(final String originalName, final String newDueDate) {
		tasks.editDueDate(originalName, newDueDate);
	}
	
	public void editTaskStatus(final String originalName, final String newStatus) {
		tasks.editStatus(originalName, newStatus);
	}
	
	public void editTaskPriority(final String originalName, final String newPriority) {
		tasks.editPriority(originalName, newPriority);
	}
	
	public void readTaskDataToSystem() throws IOException {
		tasks.readTasksFromFile();
	}
	
	public void writeTaskDataToSystem() throws IOException {
		tasks.writeTasksToFile();
	}
}