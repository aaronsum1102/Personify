package com.Personify.integration;

public class TaskInfo {
	private final String NAME;
	private final String DUE_DATE;
	private final String STATUS;
	private final String PRIORITY;
	private final String remarks;


    public TaskInfo(final String name, final String dueDate, final String status, final String priority, final String remarks) {
	    NAME = name;
		DUE_DATE = dueDate;
		STATUS = status;
		PRIORITY = priority;
        this.remarks = remarks;
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

    public String getRemarks() {
        return remarks;
    }

	@Override
	public String toString() {
		return String.format("%s;%s;%s;%s;%s", NAME, DUE_DATE, STATUS, PRIORITY, remarks);
	}
}
