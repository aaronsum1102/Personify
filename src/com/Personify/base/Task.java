package com.Personify.base;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.Personify.integration.TaskInfo;

/**
 * Task provides a object for managing a task. Each task object will hold
 * information about the task name, due date, priority and status. There is also
 * a reminder and motivational message provided in a task object depending on
 * each situation.
 *
 * @author aaronsum
 * @version 2.0, 2018-03-13
 */
public abstract class  Task {
    private String name;
    private LocalDate dueDate;
    private final Status status;
    private final Priority priority;
    private final Reminder reminder;
    private final Motivation motivation;
    String remarks;

    /**
     * Instantiate an task object with the specify elements. Due date, status and
     * priority of the task will be initialized with default value, if the specified
     * parameters are invalid.
     *
     * @param taskInfo         detail of a task. It include task name, due date, status, and
     *                         priority.
     * @param motivationQuotes is an object which hold a collections of motivational quotes.
     */
    public Task(final TaskInfo taskInfo, final Motivation motivationQuotes) {
        name = taskInfo.getTaskName();
        dueDate = setInitialDueDate(taskInfo.getTaskDueDate());
        status = new Status(taskInfo.getTaskStatus());
        priority = new Priority(taskInfo.getTaskPriority());
        reminder = new Reminder(this.dueDate);
        motivation = motivationQuotes;
        remarks = taskInfo.getRemarks();
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


    public String getRemarks() {
        return remarks;
    }

    /**
     * Provide a motivation object associated with the object.
     *
     * @return Motivation object.
     */
    public Motivation getMotivationObject() {
        return motivation;
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
            throw new IllegalArgumentException(String.format("Warning: You didn't give me a new due date. Current due date is %s.", dueDate));
        }
    }

    private boolean isDateFormatValid(final String date){
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
     * Provide a summary of the object information. It also include a reminder
     * message and motivational quotes.
     */
    public String getSummary() {
        String summary = "\nHere is a summary of the task that you had just added.\n";
        summary += String.format("Task name: %s\n", name);
        summary += String.format("Due date : %s\n", dueDate);
        summary += String.format("Status   : %s\n", status.getStatus());
        summary += String.format("Priority : %s\n", priority.getPriority());
        summary += String.format("Reminder : %s\n", reminder.getReminder());

        return summary;
    }

    /**
     * Set the status of the object.
     *
     * @param newStatus To be set for the object.
     */
    public void setStatus(final String newStatus) {
        status.setStatus(newStatus);
    }

    /**
     * Set the priority of the object.
     *
     * @param newPriority To be set for the object.
     */
    public void setPriority(final String newPriority) {
        priority.setPriority(newPriority);
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    String getMotivationalQuote() {
        if (reminder.findDaysLeft() > 1) {
            return String.format("\n\"%s\"", motivation.getQuote());
        }
        return "";
    }

    /**
     * Convert the object attributes to String.
     */
    @Override
    public String toString() {
        return String.format("%-30s%-15s%-15s%-15s", name, dueDate, status.getStatus(), priority.getPriority());
    }
}
