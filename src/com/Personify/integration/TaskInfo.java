package com.Personify.integration;

/**
 * DTO which contains information of name, due date, status, priority and remarks. It also provide
 * methods to manage these values.
 */
public class TaskInfo {
    private final String NAME;
    private final String DUE_DATE;
    private final String STATUS;
    private final String PRIORITY;
    private final String remarks;

    /**
     * Construct a <code>TaskInfo</code> object with specify parameters.
     *
     * @param name     task name to be used for construct object.
     * @param dueDate  due date to be used for construct object.
     * @param status   status to be used for construct object.
     * @param priority priority to be used for construct object.
     * @param remarks  remarks to be used for construct object.
     */
    public TaskInfo(final String name, final String dueDate, final String status, final String priority,
                    final String remarks) {
        NAME = name;
        DUE_DATE = dueDate;
        STATUS = status;
        PRIORITY = priority;
        this.remarks = remarks;
    }

    /**
     * Provide task name of this object.
     *
     * @return task name of this object.
     */
    public String getTaskName() {
        return NAME;
    }

    /**
     * Provide due date of this object.
     *
     * @return due date of this object.
     */
    public String getTaskDueDate() {
        return DUE_DATE;
    }

    /**
     * Provide status of this object.
     *
     * @return status of this object.
     */
    public String getTaskStatus() {
        return STATUS;
    }

    /**
     * Provide priority of this object.
     *
     * @return priority of this object.
     */
    public String getTaskPriority() {
        return PRIORITY;
    }

    /**
     * Provide remarks of this object.
     *
     * @return remarks of this object.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Provide String representation of this object with each field separated by ";").
     *
     * @return a string representation of this object in specific format.
     */
    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s", NAME, DUE_DATE, STATUS, PRIORITY, remarks);
    }
}
