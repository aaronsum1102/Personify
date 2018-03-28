package com.Personify.base;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * A class to manage the status of a task.
 *
 * @author aaronsum
 */
public class Status {
    private static final List<String> STATUSES = Arrays.asList("to do", "in progress", "done", "overdue");
    private String status;
    private final LocalDate dueDate;

    /**
     * Construct object with the specify value. If the value is invalid, object will
     * created with default value of "to do".
     */
    public Status(final String status, final LocalDate dueDate) {
        this.dueDate = dueDate;
        this.status = setInitialStatus(status);
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
                statuses = statuses.concat(String.format("\"%s\" or ", STATUSES.get(index)));
            } else {
                statuses = statuses.concat(String.format("\"%s\"", STATUSES.get(index)));
            }
        }
        return statuses;
    }

    private String setInitialStatus(final String status) {
        if (dueDate.compareTo(LocalDate.now()) < 0) {
            return "overdue";
        } else {
            if (isValidStatus(status)) {
                return status.toLowerCase();
            } else {
                return "to do";
            }
        }
    }

    /**
     * Provide status of object.
     *
     * @return Status of the object.
     */
    public String getStatus() {
        return status;
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

    private boolean isValidStatus(final String status) {
        return STATUSES.contains(status.toLowerCase());
    }

    private boolean isRepeatedStatus(final String newStatus) {
        return status.toLowerCase().equals(newStatus.toLowerCase());
    }
}
