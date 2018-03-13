package com.Personify.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.Personify.base.Motivation;
import com.Personify.base.Task;

public class TaskCollection {
	private TaskFileIO taskDataIO;
	private ArrayList<Task> tasks;
	private Messages messages;
	private Motivation motivationalQuotes;
	AtomicInteger taskIndex = new AtomicInteger(1);

	public TaskCollection(final Messages messages, final Motivation motivationalQuotes) {
		tasks = new ArrayList<>();
		this.messages = messages;
		this.motivationalQuotes = motivationalQuotes;
		taskDataIO = new TaskFileIO();
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public boolean addTaskToCollection(final Task task) {
		return tasks.add(task);
	}

	public boolean isNotRepeatedTaskInCollection(final String taskName) {
		return !(tasks.stream()
						.anyMatch(task -> task.getName().equals(taskName)));
	}

	private void addReminderMessageIfTaskIsEmpty() {
		messages.addMessage("You don't have any task. Do you want to add a task?");
	}

	public void getTasksSortedByName() {
		if (tasks.isEmpty()) {
			addReminderMessageIfTaskIsEmpty();
		} else {
			tasks.stream()
					.sorted((firstTaskForComparison, secondTaskForComparison) -> firstTaskForComparison.getName()
							.compareTo(secondTaskForComparison.getName()))
					.forEach(task -> messages.addMessage(String.format("%d. %s", taskIndex.getAndIncrement(), task)));
			taskIndex.set(1);
		}
	}
	
	private List<Task> findSpecificTaskByName (String name) {
		return tasks.stream()
						.filter(task -> task.getName().equals(name))
						.collect(Collectors.toList());
	}

	public void editTaskName(final String originalName, final String newName) {
		findSpecificTaskByName(originalName).stream()
											.forEach(task -> task.changeName(newName));
	}

	public void editDueDate(final String originalName, final String newDueDate) {
		findSpecificTaskByName(originalName).stream()
				.forEach(task -> task.changeDueDate(newDueDate));
	}

	public void editStatus(final String originalName, final String newStatus) {
		findSpecificTaskByName(originalName).stream()
				.forEach(task -> task.setStatus(newStatus));
	}

	public void editPriority(final String originalName, final String newPriority) {
		findSpecificTaskByName(originalName).stream()	
				.forEach(task -> task.setPriority(newPriority));
	}
	
	public void readTasksFromFile() throws IOException {
		taskDataIO.readTasksDataFromDataFile();
		List<TaskInfo> tasksToRead = taskDataIO.getTasksData();
		if (!tasksToRead.isEmpty()) {
			tasksToRead.stream()
						.forEach(taskData -> {
												Task task = new Task(taskData, messages, motivationalQuotes);
												addTaskToCollection(task);
									 		 });
		}
	}
	
	private List<TaskInfo> prepareTasksDataForArchive() {
		List<TaskInfo> tasksForArchive = new ArrayList<>();
		tasks.stream().forEach(task -> {
			String taskName = task.getName();
			String dueDate = String.format("%s", task.getDueDate());
			String status = task.getStatus().getStatus();
			String priority = task.getPrioirty().getPriority();
			tasksForArchive.add(new TaskInfo(taskName, dueDate, status, priority));
		});
		return tasksForArchive;
	}
	
	public void writeTasksToFile() throws IOException {
		List<TaskInfo> tasksToArchive = prepareTasksDataForArchive();
		taskDataIO.archiveTasks(tasksToArchive);
	}

}
