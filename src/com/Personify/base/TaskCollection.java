package com.Personify.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.Personify.integration.Messenger;
import com.Personify.integration.TaskFileIO;
import com.Personify.integration.TaskInfo;

public class TaskCollection {
	private TaskFileIO taskDataIO;
	private List<Task> tasks;
	private Messenger messenger;
	private Motivation motivationalQuotes;
	AtomicInteger taskIndex = new AtomicInteger(1);

	public TaskCollection(final Messenger messenger, final Motivation motivationalQuotes) {
		tasks = new ArrayList<>();
		this.messenger = messenger;
		this.motivationalQuotes = motivationalQuotes;
		taskDataIO = new TaskFileIO();
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public boolean addTaskToCollection(final Task task) {
		if (isTaskValid(task.getName())) {
			messenger.addMessage(String.format("Task added. Total task number: %d.", tasks.size()));
			task.getSummary();
			return tasks.add(task);
		} else if (Task.isNameNotEmptyString(task.getName())) {
			messenger.addMessage("You have the same task in record. Please give me a new name.");
		} else {
			messenger.addMessage("I can't understand the task name.");
		}
		return false;
	}

	private boolean isTaskValid(final String taskName) {
		if (Task.isNameNotEmptyString(taskName) && isNotRepeatedTaskInCollection(taskName)) {
			return true;
		}
		return false;
	}

	private boolean isNotRepeatedTaskInCollection(final String taskName) {
		return !(tasks.stream().anyMatch(task -> task.getName().equals(taskName)));
	}

	private void addReminderMessageIfTasksIsEmpty() {
		messenger.addMessage("You don't have any task. Do you want to add a task?");
	}
	
	private void getTasksIntoFormat(List<Task> tasks) {
		messenger.addMessage("   Task Name; Due Date; Status; Priority");
		tasks.stream()
			.forEach(task -> messenger.addMessage(String.format("%d. %s", taskIndex.getAndIncrement(), task)));
		taskIndex.set(1);
	}

	private void sortTasksByDueDate(List<Task> tasks) {
		tasks.sort((firstTask, secondTask) -> Long.compare(firstTask.getReminderObject().findDaysLeft(), secondTask.getReminderObject().findDaysLeft()));
		Collections.reverse(tasks);
	}
	
	private void sortTasksByDueDateForDisplay(List<Task> tasks) {
		if (tasks.isEmpty()) {
			addReminderMessageIfTasksIsEmpty();
		} else {
			sortTasksByDueDate(tasks);
			getTasksIntoFormat(tasks);
		}
	}
	
	public void getAllTasks() {
		sortTasksByDueDateForDisplay(tasks);
	}
	
	public void getTasksWithSpecificStatus(String status) {
		List<Task> filteredTasks = tasks.stream()
										.filter(task -> task.getStatusObject().getStatus().equals(status))
										.collect(Collectors.toList());
		sortTasksByDueDateForDisplay(filteredTasks);
	}
	
	public void getTasksToComplete() {
		List<Task> filteredTasks = tasks.stream()
										.filter(task -> !(task.getStatusObject().getStatus().equals("done")))
										.collect(Collectors.toList());
		sortTasksByDueDateForDisplay(filteredTasks);
	}

	public boolean isTaskNameValidForEdit(final String taskName) {
		if (isTaskValid(taskName)) {
			return true;
		} else if (!isNotRepeatedTaskInCollection(taskName)) {
			messenger.addMessage(String.format("No task with the name of \"%s\".", taskName));
		} else {
			messenger.addMessage("I can't understand the task name.");
		}
		return false;
	}

	public void editTaskName(final int taskIndex, final String newName) {
		Task taskToEdit = tasks.get(taskIndex - 1);
		taskToEdit.changeName(newName);
	}

	public void editDueDate(final int taskIndex, final String newDueDate) {
		Task taskToEdit = tasks.get(taskIndex - 1);
		taskToEdit.changeDueDate(newDueDate);
	}

	public void editStatus(final int taskIndex, final String newStatus) {
		Task taskToEdit = tasks.get(taskIndex - 1);
		taskToEdit.setStatus(newStatus);
	}

	public void editPriority(final int taskIndex, final String newPriority) {
		Task taskToEdit = tasks.get(taskIndex - 1);
		taskToEdit.setPriority(newPriority);
	}

	private void readTasksToCollection(Task task) {
		tasks.add(task);
	}

	public void readTasksFromFile() throws IOException {
		taskDataIO.readTasksDataFromDataFile();
		List<TaskInfo> tasksToRead = taskDataIO.getTasksData();
		if (!tasksToRead.isEmpty()) {
			tasksToRead.stream().forEach(taskData -> {
				Task task = new Task(taskData, messenger, motivationalQuotes);
				readTasksToCollection(task);
			});
		}
	}

	private List<TaskInfo> prepareTasksDataForArchive() {
		List<TaskInfo> tasksForArchive = new ArrayList<>();
		tasks.stream().forEach(task -> {
			String taskName = task.getName();
			String dueDate = String.format("%s", task.getDueDate());
			String status = task.getStatusObject().getStatus();
			String priority = task.getPrioirtyObject().getPriority();
			tasksForArchive.add(new TaskInfo(taskName, dueDate, status, priority));
		});
		return tasksForArchive;
	}

	public void writeTasksToFile() throws IOException {
		List<TaskInfo> tasksToArchive = prepareTasksDataForArchive();
		taskDataIO.archiveTasks(tasksToArchive);
	}
	
	public void removeAllTasks() {
		if (tasks.isEmpty()) {
			messenger.addMessage("You have no task to remove.");
		} else {
			tasks.clear();
			messenger.addMessage("All tasks were removed.");
		}
	}
}
