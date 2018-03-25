package com.Personify.base;

import java.util.*;

/**
 * A class to manage the status of a task.
 *
 * @author aaronsum
 * @version 2.0, 2018-03-13
 */
public class Status {
    private static final List<String> STATUSES = Arrays.asList("to do", "in progress", "done", "overdue");
    private String status;

    /**
     * Construct object with the specify value. If the value is invalid, object will
     * created with default value of "to do".
     */
    public Status(final String status) {
        this.status = setInitialStatus(status);
    }

    private String setInitialStatus(final String status) {
        if (isValidStatus(status)) {
            return status.toLowerCase();
        } else {
            return "to do";
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
        return STATUSES.contains(status.toLowerCase());
    }

    private boolean isRepeatedStatus(final String newStatus) {
        return status.toLowerCase().equals(newStatus.toLowerCase());
    }

    /**
     * Modify the status with the specify element. If it is a valid status and
     * is unique, then the status will be updated.
     *
     * @param newStatus Element for modification of the existing status.
     */
    public void setStatus(final String newStatus) {
        if (newStatus.isEmpty()) {
            throw new IllegalArgumentException("Warning: Hey, you didn't type in anything!");
        } else if (isValidStatus(newStatus)) {
            if (!isRepeatedStatus(newStatus)) {
                status = newStatus.toLowerCase();
            } else {
                throw new IllegalArgumentException("Warning: You didn't give me a new status.");
            }
        } else {
            throw new IllegalArgumentException("Warning: That is not a valid status!");
        }
    }
}
