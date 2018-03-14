package com.Personify.base;

import java.util.*;
import com.Personify.integration.Messenger;

/**
 * Priority responsible for the management of the priority of a task.
 * 
 * @author aaronsum
 * @version 2.0, 2018-03-13
 */
public class Priority {
	private static final List<String> PRIORITIES = Arrays.asList("low", "medium", "high");
	private String priority;
	private Messenger messenger;

	/**
	 * Construct object with the specify value. If the value is invalid, the object
	 * will be instantiated with default value of "high".
	 */
	public Priority(final Messenger messenger, final String priority) {
		this.messenger = messenger;
		setInitialPriority(priority);
	}

	private void setInitialPriority(final String priority) {
		if (isValidPriority(priority)) {
			this.priority = priority;
		} else {
			this.priority = "high";
			messenger.addMessage("Invalid priority given. I had set the priority as \"high\".");
		}
	}

	/**
	 * Provide a formatted string of all valid priorities.
	 * 
	 * @return All valid priorities.
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
	 * Provide priority status of an object.
	 * 
	 * @return Priority status.
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Check if the specify element is one of the allowable priority.
	 * 
	 * @param priority
	 *            Element to be checked.
	 * @return true If the specify element is in default list.
	 */
	private boolean isValidPriority(final String priority) {
		if (PRIORITIES.contains(priority.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the specify element is the same as existing priority.
	 * 
	 * @param newPriority
	 *            Element to be checked.
	 * @return true If the specify element is repeated.
	 */
	private boolean isRepeatedPriority(final String newPriority) {
		if (priority.toLowerCase().equals(newPriority.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Modify the priority with the specify element. If it is one of the valid
	 * priority in the list and is unique regardless of its letter case, then the
	 * priority will be updated.
	 * 
	 * @param newPriority
	 *            Element for modification of the existing priority.
	 * @return true If priority was modified.
	 */
	public boolean setPriority(final String newPriority) {
		if (isValidPriority(newPriority)) {
			if (!isRepeatedPriority(newPriority)) {
				priority = newPriority.toLowerCase();
				messenger.addMessage("Successfully change the priority of the task.");
				return true;
			} else {
				messenger.addMessage(String
						.format("Priority is the same as before. Current priority for the task is \"%s\".", priority));
			}
		} else {
			messenger.addMessage(
					String.format("Invalid priority given. Current priority for the task is \"%s\".", priority));
		}
		return false;
	}
}
