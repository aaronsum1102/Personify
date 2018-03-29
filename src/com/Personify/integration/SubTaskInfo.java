package com.Personify.integration;

/**
 * DTO to be used by <code>WorkTask</code>, <code>PersonalTask</code> object.
 */
public class SubTaskInfo {
    private final String className;
    private final TaskInfo taskInfo;
    private final String typeSpecificAttribute;

    /**
     * Construct a <code>SubTaskInfo</code> object with information about class name, <code>TaskInfo</code> object and
     * type specific attribute to be used by <code>WorkTask</code> or <code>PersonalTask</code> object.
     *
     * @param className             a String representation of class name as either "WorkTask" or "PersonalTask".
     * @param taskInfo              {@link TaskInfo} object.
     * @param typeSpecificAttribute a String of information to be used by <code>WorkTask</code>,
     *                              <code>PersonalTask</code> object.
     */
    SubTaskInfo(final String className, final TaskInfo taskInfo, final String typeSpecificAttribute) {
        this.className = className;
        this.taskInfo = taskInfo;
        this.typeSpecificAttribute = typeSpecificAttribute;
    }

    /**
     * Provide class name of this <code>SubTaskInfo</code> object.
     *
     * @return a String representation of class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Provide {@link TaskInfo} object of this <code>SubTaskInfo</code> object.
     *
     * @return a <code>SubTaskInfo</code> object.
     */
    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    /**
     * Provide information to be used by <code>WorkTask</code> or <code>PersonalTask</code> object.
     *
     * @return a String of information to be used by <code>WorkTask</code> or <code>PersonalTask</code> object.
     */
    public String getTypeSpecificAttribute() {
        return typeSpecificAttribute;
    }

    /**
     * Provide a string representation of this object. It contains information of class name, string representation
     * from {@link TaskInfo#toString()} and typeSpecificAttribute of these object.
     *
     * @return a string representation of this object.
     */
    @Override
    public String toString() {
        return String.format("%s;%s;%s", className, taskInfo, typeSpecificAttribute);
    }
}
