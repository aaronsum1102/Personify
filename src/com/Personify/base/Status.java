package com.Personify.base;

import java.util.*;
import com.Personify.integration.Messenger;

/**
 * A class to manage the status of a task.
 * 
 * @author aaronsum
 * @version 2.0, 2018-03-13
 */
public class Status {
	private static final List<String> STATUSES = Arrays.asList("to do", "in progress", "done", "overdue");
	private String status;
	private Messenger messenger;

	/**
	 * Construct object with the specify value. If the value is invalid, object will
	 * created with default value of "to do".
	 */
	public Status(final Messenger messenger, final String status) {
		this.messenger = messenger;
		setInitialStatus(status);
	}

	private void setInitialStatus(final String status) {
		if (isValidStatus(status)) {
			this.status = status;
		} else {
			this.status = "to do";
			messenger.addMessage("Invalid status given. I had set the status as \"to do\".");
		}
	}

	/**
	 * Provide a string of all valid status.
	 * 
	 * @return all valid status.
	 */
	public static String getStatuses() {
		String statuses = "";
		for (int index = 0; index <= STATUSES.size() - 1; index++) {
			if (index != (STATUSES.size() - 1)) {
				statuses += String.format("\"%s\" or ", STATUSES.get(index));
			} else {
				statuses += String.format("\"%s\"", STATUSES.get(index));
			}
		}
		return statuses;
	}

	/**
	 * Provide status of object.
	 * 
	 * @return Status of the object.
	 */
	public String getStatus() {
		return status;
	}

	private boolean isValidStatus(final String status) {
		if (STATUSES.contains(status.toLowerCase())) {
			return true;
		}
		return false;
	}

	private boolean isRepeatedStatus(final String newStatus) {
		if (status.toLowerCase().equals(newStatus.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Modify the status with the specify element. If it is a valid status and
	 * is unique, then the status will be updated.
	 * 
	 * @param newStatus
	 *            Element for modification of the existing status.
	 * @return true If status was modified.
	 */
	public boolean setStatus(final String newStatus) {
		if (isValidStatus(newStatus)) {
			if (!isRepeatedStatus(newStatus)) {
				status = newStatus.toLowerCase();
				messenger.addMessage("Successfully change the status of the task.");
				return true;
			} else {
				messenger.addMessage(
						String.format("Status is the same as before. Current status for the task is \"%s\".", status));
			}
		} else {
			messenger.addMessage(String.format("Invalid status. Current status for the task is \"%s\".", status));
		}
		return false;
	}
}
