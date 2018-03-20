package com.Personify.controller;

import java.io.IOException;
import java.util.List;
import com.Personify.base.Motivation;
import com.Personify.base.Task;
import com.Personify.base.TaskCollection;
import com.Personify.integration.TaskInfo;
import com.Personify.integration.Messenger;

public class Controller {
	private TaskCollection tasks;
	private Messenger messenger;
	private Motivation motivationalQuotes;

	public Controller() throws IOException {
		messenger = new Messenger();
		motivationalQuotes = new Motivation();
		tasks = new TaskCollection(messenger, motivationalQuotes);
	}

	public Messenger getMessages() {
		return messenger;
	}

	public TaskCollection getTasks() {
		return tasks;
	}

	public List<String> getSystemMessages() {
		return messenger.getMessages();
	}

	public List<String> getAllTasksSortedByName() {
		tasks.getTasksSortedByName();
		return messenger.getMessages();
	}

	public void clearMessages() {
		messenger.clearMessages();
	}
	
	public void addTask(final TaskInfo TaskInfo) throws IOException {
		Task task = new Task(TaskInfo, messenger, motivationalQuotes);
		tasks.addTaskToCollection(task);
	}

	public boolean isTaskNameValidForEdit(final String taskName) {
		return tasks.isTaskNameValidForEdit(taskName);
	}

	public void editTaskName(final int index, final String newName) {
		tasks.editTaskName(index, newName);
	}
	
	public void editDueDate(final int index, final String newDueDate) {
		tasks.editDueDate(index, newDueDate);
	}
	
	public void editTaskStatus(final int index, final String newStatus) {
		tasks.editStatus(index, newStatus);
	}
	
	public void editTaskPriority(final int index, final String newPriority) {
		tasks.editPriority(index, newPriority);
	}
	
	public void readTaskDataToSystem() throws IOException {
		tasks.readTasksFromFile();
	}
	
	public void writeTaskDataToSystem() throws IOException {
		tasks.writeTasksToFile();
	}
}