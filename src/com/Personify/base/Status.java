package com.Personify.base;

import java.util.*;
import com.Personify.integration.Messages;

/**
 * Status provides object to manage status of a task. It has default value of
 * "to do".
 * 
 * @author aaronsum
 * @version 1.0, 2018-03-05
 */
public class Status {
	private static final List<String> STATUSES = Arrays.asList("to do", "in progress", "done", "overdue");
	private String status;
	private Messages messages;

	/**
	 * Construct object with default value of "to do".
	 */
	public Status(final Messages messages, final String status) {
		this.messages = messages;
		if (isValidStatus(status)) {
			this.status = status;
		} else {
			this.status = "to do";
			messages.addMessage("Invalid status given. I had set the status as \"to do\".");
		}
	}

	/**
	 * Provide a string of all valid statuses.
	 * 
	 * @return all valid statuses.
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
	 * Provide status the object.
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
	 * Modify the status with the specify element if it is a valid status and
	 * different from existing regardless of its letter case.
	 * 
	 * @param newStatus
	 *            Element for modification of the existing status.
	 * @return true if status was modified.
	 */
	public boolean setStatus(final String newStatus) {
		if (isValidStatus(newStatus)) {
			if (!isRepeatedStatus(newStatus)) {
				status = newStatus.toLowerCase();
				messages.addMessage("Successfully change the status of the task.");
				return true;
			} else {
				messages.addMessage(
						String.format("Status is the same as before. Current status for the task is \"%s\".", status));
			}
		} else {
			messages.addMessage(String.format("Invalid status. Current status for the task is \"%s\".", status));
		}
		return false;
	}
}
