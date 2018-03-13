package com.Personify.base;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import com.Personify.integration.TaskInfo;
import com.Personify.integration.Messages;

/**
 * Task provides an object for managing a task. Each task object will hold
 * information about the task name, due date, priority and status. There is also
 * a reminder and motivational message provided in a task object depending on
 * each situation.
 * 
 * @author aaronsum
 * @version 1.1, 2018-03-13
 *
 */
public class Task {
	private String name;
	private LocalDate dueDate;
	private Status status;
	private Priority priority;
	private Reminder reminder;
	private Motivation motivation;
	private Messages messages;

	/**
	 * Construct an task object with the specify elements. Due date, status and
	 * priority of the task will be initialized with default value, if the specified
	 * parameters are invalid.
	 * 
	 * @param taskInfo
	 *            detail of a task. It include task name, due date, status, and
	 *            priority.
	 * @param messages
	 *            is an object which hold all the messages generated in system
	 *            operation.
	 * @param motivationalQuotes
	 *            is an object which hold a collections of motivational quotes.
	 */
	public Task(final TaskInfo taskInfo, final Messages messages, final Motivation motivationQuotes)
			throws DateTimeParseException {
		this.messages = messages;
		name = taskInfo.getTaskName();
		dueDate = setInitialDueDate(taskInfo.getTaskDueDate());
		status = new Status(messages, taskInfo.getTaskStatus());
		priority = new Priority(messages, taskInfo.getTaskPriority());
		reminder = new Reminder(this.dueDate);
		motivation = motivationQuotes;
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
	public Status getStatus() {
		return status;
	}

	/**
	 * Provide a priority object associated with the object.
	 * 
	 * @return Priority object.
	 */
	public Priority getPrioirty() {
		return priority;
	}

	/**
	 * Provide a reminder object associated with the object.
	 * 
	 * @return Reminder object.
	 */
	public Reminder getReminder() {
		return reminder;
	}

	/**
	 * Provide a motivation object associated with the object.
	 * 
	 * @return Motivation object.
	 */
	public Motivation getMotivation() {
		return motivation;
	}

	/**
	 * Provide a message object associated with task. A message object hold messages
	 * generated during system operation.
	 * 
	 * @return Reminder object.
	 */
	public Messages getMessages() {
		return messages;
	}

	/**
	 * Check the specified element is non empty String.
	 * 
	 * @param name
	 *            String to be checked.
	 * @return true if the specified element is not empty.
	 */
	public static boolean isNameNotEmptyString(final String name) {
		return !name.isEmpty();
	}

	private boolean isNameValid(final String name) {
		if (!(this.name.toLowerCase().equals(name.toLowerCase())) && isNameNotEmptyString(name)) {
			return true;
		}
		return false;
	}

	/**
	 * Modify name of object if the name is unique and is not empty.
	 * 
	 * @param newName
	 *            Element for modification of object's name.
	 * @return true if object's name was changed.
	 */
	public boolean changeName(final String newName) {
		if (isNameValid(newName)) {
			name = newName;
			messages.addMessage("Successfully change task name.");
			return true;
		} else {
			messages.addMessage("You have the same task in your list.");
			return false;
		}
	}

	/**
	 * Modify due date of object if the specify element is unique.
	 * 
	 * @param newDueDate
	 *            Element for modification of due date.
	 * @return true if the due date was changed.
	 */
	public boolean changeDueDate(final String newDueDate) {
		if (!(dueDate.toString().equals(newDueDate)) && isDateFormatValid(newDueDate)) {
			dueDate = LocalDate.parse(newDueDate);
			messages.addMessage("Successfully change to new due date.");
			return true;
		} else if (dueDate.toString().equals(newDueDate)) {
			messages.addMessage(String.format("Due date given is same as before. Current due date is %s.", dueDate));
			return false;
		} else {
			messages.addMessage(String.format("Invalid due date format."));
			return false;
		}
	}

	private boolean isDateFormatValid(final String date) throws DateTimeParseException {
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
		} else {
			messages.addMessage(
					String.format("Invlid date format given. I had set the due date as \"%s\".", LocalDate.now()));
			return LocalDate.now();
		}
	}

	/**
	 * Provide a summary of the object information. It also include a reminder
	 * message and motivational quotes.
	 */
	public void getSummary() {
		messages.addMessage("\nHere is a summary of the task that you had just added.");
		messages.addMessage(String.format("Task name: %s", name));
		messages.addMessage("Due date : " + dueDate);
		messages.addMessage("Status   : " + status.getStatus());
		messages.addMessage("Priority : " + priority.getPriority());
		messages.addMessage("Reminder : " + reminder.getMessage());
		if (reminder.findDaysLeft() > 1) {
			messages.addMessage(String.format("\n\"%s\"", motivation.getQuote()));
		}
	}

	/**
	 * Set the status of the object.
	 * 
	 * @param newStatus
	 *            to be set for the object.
	 */
	public void setStatus(final String newStatus) {
		status.setStatus(newStatus);
	}

	/**
	 * Set the priority of the object.
	 * 
	 * @param newPriority
	 *            to be set for the object.
	 */
	public void setPriority(final String newPriority) {
		priority.setPriority(newPriority);
	}
	
	/**
	 * Convert the object to String.
	 */
	@Override
	public String toString() {
		return String.format("%s; %s; %s; %s.", name, dueDate, status.getStatus(), priority.getPriority());
	}
}
