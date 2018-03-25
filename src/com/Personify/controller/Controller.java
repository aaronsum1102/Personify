package com.Personify.controller;

import java.io.IOException;
import java.util.List;

import com.Personify.base.Motivation;
import com.Personify.base.Task;
import com.Personify.base.TaskCollection;
import com.Personify.integration.TaskInfo;

public class Controller {
    private TaskCollection tasks;
    private final Motivation motivationalQuotes;

    public Controller() throws IOException {
        motivationalQuotes = new Motivation();
        tasks = new TaskCollection(motivationalQuotes);
    }

    public int getTasksSize() {
        return tasks.getTasksSize();
    }

    public List<Task> getAllTasks() {
        return tasks.getAllTasks();
    }

    public List<Task> getTasksWithSpecificStatus(String status) {
        return tasks.getTasksWithSpecificStatus(status);
    }

    public List<Task> getTasksToComplete() {
        return tasks.getTasksToComplete();
    }

    public String addTaskAndGetSummary(final TaskInfo TaskInfo) throws IllegalArgumentException {
        Task task = new Task(TaskInfo, motivationalQuotes);
        return tasks.getAddTaskSummary(task);
    }

    public boolean isTaskNameValid(final String taskName) {
        return tasks.isTaskNameValid(taskName);
    }

    public void editTaskName(final int index, final String newName) {
        tasks.editTaskName(index, newName);
    }

    public void editDueDate(final int index, final String newDueDate) throws IllegalArgumentException {
        tasks.editDueDate(index, newDueDate);
    }

    public void editTaskStatus(final int index, final String newStatus) {
        tasks.editStatus(index, newStatus);
    }

    public void editTaskPriority(final int index, final String newPriority) {
        tasks.editPriority(index, newPriority);
    }

    public void readTaskDataToSystem() throws IOException {
        tasks.readTasksFromFile();
    }

    public void writeTaskDataToSystem() {
        tasks.writeTasksToFile();
    }

    public boolean removeAllTasks() {
        return tasks.removeAllTasks();
    }

    public void deleteSpecificTask(int index) {
        tasks.deleteSpecificTask(index);
    }
}