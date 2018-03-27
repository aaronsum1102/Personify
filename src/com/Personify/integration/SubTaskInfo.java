package com.Personify.integration;

public class SubTaskInfo {
    private String className;
    private TaskInfo taskInfo;
    private String typeSpecificAttribute;

    SubTaskInfo(String className, TaskInfo taskInfo, String typeSpecificAttribute) {
        this.className = className;
        this.taskInfo = taskInfo;
        this.typeSpecificAttribute = typeSpecificAttribute;
    }

    public String getClassName() {
        return className;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public String getTypeSpecificAttribute() {
        return typeSpecificAttribute;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s", className, taskInfo, typeSpecificAttribute);
    }
}