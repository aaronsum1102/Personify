package com.Personify.base;

import java.util.*;
import com.Personify.integration.Messages;

/**
 * Priority provides object to manage priority status of a task. It has a
 * default priority of "high".
 * 
 * @author aaronsum
 * @version 1.0, 2018-03-05
 */
public class Priority {
	private static final List<String> PRIORITIES = Arrays.asList("low", "medium", "high");
	private String priority;
	private Messages messages;

	/**
	 * Construct object with default priority of "high".
	 */
	public Priority(final Messages messages, final String priority) {
		this.messages = messages;
		if (isValidPriority(priority)) {
			this.priority = priority;
		} else {
			this.priority = "high";
			messages.addMessage("Invalid priority given. I had set the priority as \"high\".");
		}
	}

	/**
	 * Provide a formatted string of valid priorities.
	 * 
	 * @return all valid priorities.
	 */
	public static String getPriorities() {
		String priorities = "";
		for (int index = 0; index <= PRIORITIES.size() - 1; index++) {
			if (index != (PRIORITIES.size() - 1)) {
				priorities += String.format("\"%s\" or ", PRIORITIES.get(index));
			} else {
				priorities += String.format("\"%s\"", PRIORITIES.get(index));
			}
		}
		return priorities;
	}

	/**
	 * Provide priority status of object.
	 * 
	 * @return Priority status.
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Access if the specify element is one of the allowable priority.
	 * 
	 * @param priority
	 *            Element to be checked.
	 * @return true if the specify element is in default list.
	 */
	private boolean isValidPriority(final String priority) {
		if (PRIORITIES.contains(priority.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Access if the specify element is the same as existing priority.
	 * 
	 * @param newPriority
	 *            Element to be checked.
	 * @return true if the specify element is repeated.
	 */
	private boolean isRepeatedPriority(final String newPriority) {
		if (priority.toLowerCase().equals(newPriority.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Modify the priority with the specify element, if it is a valid priority and
	 * is different from existing priority regardless of its letter case.
	 * 
	 * @param newPriority
	 *            Element for modification of the existing priority.
	 * @return true if priority was modified.
	 */
	public boolean setPriority(final String newPriority) {
		if (isValidPriority(newPriority)) {
			if (!isRepeatedPriority(newPriority)) {
				priority = newPriority.toLowerCase();
				messages.addMessage("Successfully change the priority of the task.");
				return true;
			} else {
				messages.addMessage(String
						.format("Priority is the same as before. Current priority for the task is \"%s\".", priority));
			}
		} else {
			messages.addMessage(
					String.format("Invalid priority given. Current priority for the task is \"%s\".", priority));
		}
		return false;
	}
}
