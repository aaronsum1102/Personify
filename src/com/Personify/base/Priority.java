package com.Personify.base;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Responsible for the management of the priority of a task.
 */
public class Priority {
    private static final List<String> PRIORITIES = Arrays.asList("low", "medium", "high");
    private final LocalDate dueDate;
    private String priority;

    /**
     * Construct object with the specify value. If the value is invalid, or the due date of the task is behind current
     * date the object will be instantiated with default value of "high".
     *
     * @param priority priority to set for a specific <code>Task</code> object.
     * @param dueDate  due date of a specific <code>Task</code> object.
     */
    public Priority(final String priority, final LocalDate dueDate) {
        this.dueDate = dueDate;
        this.priority = setInitialPriority(priority);
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
                priorities = priorities.concat(String.format("\"%s\" or ", PRIORITIES.get(index)));
            } else {
                priorities = priorities.concat(String.format("\"%s\"", PRIORITIES.get(index)));
            }
        }
        return priorities;
    }

    private String setInitialPriority(final String priority) {
        if (dueDate.compareTo(LocalDate.now()) < 0) {
            return "high";
        } else {
            if (isValidPriority(priority)) {
                return priority.toLowerCase();
            } else {
                return "high";
            }
        }
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
     * Modify the priority with the specify element. If it is one of the valid priority in the list and is unique
     * regardless of its letter case, then the priority will be updated.
     *
     * @param newPriority Element for modification of the existing priority.
     */
    public void setPriority(final String newPriority) {
        if (newPriority.isEmpty()) {
            throw new IllegalArgumentException("Warning: Hey, you didn't type in anything!");
        } else if (isValidPriority(newPriority)) {
            if (!isRepeatedPriority(newPriority)) {
                priority = newPriority.toLowerCase();
            } else {
                throw new IllegalArgumentException("Warning: You didn't give me a new priority.");
            }
        } else {
            throw new IllegalArgumentException("Warning: That is not a valid priority.");
        }
    }

    private boolean isValidPriority(final String priority) {
        return PRIORITIES.contains(priority.toLowerCase());
    }

    private boolean isRepeatedPriority(final String newPriority) {
        return priority.toLowerCase().equals(newPriority.toLowerCase());
    }
}
