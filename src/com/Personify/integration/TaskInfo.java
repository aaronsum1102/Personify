package com.Personify.integration;

public class TaskInfo {
	private final String NAME;
	private final String DUE_DATE;
	private final String STATUS;
	private final String PRIORITY;

	public TaskInfo(String name, String dueDate, String status, String priority) {
		NAME = name;
		DUE_DATE = dueDate;
		STATUS = status;
		PRIORITY = priority;
	}

	public String getTaskName() {
		return NAME;
	}

	public String getTaskDueDate() {
		return DUE_DATE;
	}

	public String getTaskStatus() {
		return STATUS;
	}

	public String getTaskPriority() {
		return PRIORITY;
	}
	
	@Override
	public String toString() {
		return String.format("%s;%s;%s;%s", NAME, DUE_DATE, STATUS, PRIORITY);
	}
}
