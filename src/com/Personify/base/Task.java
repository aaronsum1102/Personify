package com.Personify.base;

import com.Personify.integration.TaskInfo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Task provides an object for managing a task. Each task object will hold information about the task name, due date,
 * priority, status and remarks of a task. There is also a reminder and motivational message provided in a task object
 * depending on each situation.
 */
public abstract class Task {
    final Reminder reminder;
    private final Status status;
    private final Priority priority;
    private final Motivation motivation;
    String remarks;
    private String name;
    private LocalDate dueDate;

    /**
     * Instantiate a task object with task information, and motivational quotes.
     *
     * @param taskInfo         Detail information of a task.
     * @param motivationQuotes An object which hold a collections of motivational quotes.
     */
    public Task(final TaskInfo taskInfo, final Motivation motivationQuotes) {
        name = taskInfo.getTaskName();
        dueDate = setInitialDueDate(taskInfo.getTaskDueDate());
        status = new Status(taskInfo.getTaskStatus(), dueDate);
        priority = new Priority(taskInfo.getTaskPriority(), dueDate);
        reminder = new Reminder(this.dueDate);
        motivation = motivationQuotes;
        remarks = taskInfo.getRemarks();
    }

    /**
     * Check the specified element is an non empty String.
     *
     * @param name String to be checked.
     * @return true If the specified element is not empty.
     */
    public static boolean isNameNotEmptyString(final String name) {
        return !name.isEmpty();
    }

    /**
     * Provide task name.
     *
     * @return Task name.
     */
    public String getName() {
        return name;
    }

    /**
     * Provide due date.
     *
     * @return Due date.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Provide a status object associated with the object.
     *
     * @return Status object.
     */
    public Status getStatusObject() {
        return status;
    }

    /**
     * Provide a priority object associated with the object.
     *
     * @return Priority object.
     */
    public Priority getPriorityObject() {
        return priority;
    }

    /**
     * Provide a reminder object associated with the object.
     *
     * @return Reminder object.
     */
    public Reminder getReminderObject() {
        return reminder;
    }

    /**
     * Provide remarks associated with the object.
     *
     * @return Remarks of the task.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Set the remarks associated with the object.
     *
     * @param remarks new Remarks of the object.
     */
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    private boolean isNameValid(final String name) {
        return !(this.name.toLowerCase().equals(name.toLowerCase())) && isNameNotEmptyString(name);
    }

    /**
     * Modify name of object if the name is unique and is not empty.
     *
     * @param newName Element for modification of object's name.
     * @return true If task name was changed.
     */
    public boolean changeName(final String newName) {
        if (isNameValid(newName)) {
            name = newName;
            return true;
        }
        return false;
    }

    /**
     * Modify due date of object if the specify element is unique.
     *
     * @param newDueDate Element for modification of due date.
     */
    public void changeDueDate(final String newDueDate) {
        if (!newDueDate.isEmpty() && !(dueDate.toString().equals(newDueDate)) && isDateFormatValid(newDueDate)) {
            dueDate = LocalDate.parse(newDueDate);
        } else if (newDueDate.isEmpty()) {
            throw new IllegalArgumentException("Warning: Hey, you didn't type in anything!");
        } else if (isDateFormatValid(newDueDate)) {
            throw new IllegalArgumentException("Warning: You didn't give me a date with correct format.");
        } else {
            throw new IllegalArgumentException(String.format("Warning: You didn't give me a new due date. " +
                    "Current due date is %s.", dueDate));
        }
    }

    private boolean isDateFormatValid(final String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private LocalDate setInitialDueDate(final String dueDate) {
        if (isDateFormatValid(dueDate)) {
            return LocalDate.parse(dueDate);
        }
        return LocalDate.now();
    }

    /**
     * Provide a String representation of the object with specific formatting on the name of the fields. Information in
     * the string include task name, due date, status and priority of a <code>Task</code> object.
     *
     * @return a String representation of this object.
     */
    public String getSummary() {
        String summary = "Here is a summary of the task that you had just added.\n";
        summary += String.format("%-12s%-2s%s\n", "Task name", ":", name);
        summary += String.format("%-12s%-2s%s\n", "Due date", ":", dueDate);
        summary += String.format("%-12s%-2s%s\n", "Status", ":", status.getStatus());
        summary += String.format("%-12s%-2s%s\n", "Priority", ":", priority.getPriority());
        return summary;
    }

    /**
     * Call {@link Status#setStatus(String)} method to set the status of the object.
     *
     * @param newStatus new status to be set for the object.
     */
    public void setStatus(final String newStatus) {
        status.setStatus(newStatus);
    }

    /**
     * Call {@link Priority#setPriority(String)} method to set the priority of the object.
     *
     * @param newPriority new priority to be set for the object.
     */
    public void setPriority(final String newPriority) {
        priority.setPriority(newPriority);
    }

    String getMotivationalQuote() {
        if (reminder.findDaysLeft() > 1) {
            return motivation.getQuote();
        }
        return "";
    }

    /**
     * Return a String representation of this object. The String representation consist task name, due date, status
     * and priority of the object.
     */
    @Override
    public String toString() {
        return String.format("%-30s%-15s%-15s%-15s", name, dueDate, status.getStatus(), priority.getPriority());
    }
}
