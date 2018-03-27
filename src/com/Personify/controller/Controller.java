package com.Personify.controller;

import java.io.IOException;
import java.util.List;

import com.Personify.base.*;
import com.Personify.integration.TaskInfo;

public class Controller {
    private TaskCollection tasks;
    private Motivation motivationalQuotes;
    private final UserManagement userManagement;

    public Controller() throws IOException {
        userManagement = new UserManagement();
    }

    public void afterLogIn(final String userProfileName) throws IOException {
        motivationalQuotes = new Motivation();
        tasks = new TaskCollection(motivationalQuotes, userProfileName);
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

    public String addPersonalTaskAndGetSummary(final TaskInfo TaskInfo, final String details) throws IllegalArgumentException {
        Task task = new PersonalTask(TaskInfo, motivationalQuotes, details);
        return tasks.getAddTaskSummary(task);
    }

    public String addWorkTaskAndGetSummary(final TaskInfo TaskInfo, final String collaborator) throws IllegalArgumentException {
        Task task = new WorkTask(TaskInfo, motivationalQuotes);
        ((WorkTask)task).addCollaborators(collaborator);
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

    public void readTaskDataToSystem() {
        tasks.readTasksToSystem();
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

    public void deleteTasksThatWereDone(List<Task> filteredTasks) {
        tasks.deleteTasksThatWereDone(filteredTasks);
    }

    public boolean logIn(final String userName, final String password) {
        return userManagement.logIn(userName, password);
    }

    public void saveUserProfile() {
        userManagement.saveUserProfile();
    }

    public void createUser(final String userName, final String password) throws IllegalUserInfoException {
        userManagement.createUser(userName, password);
    }

    public boolean validateNewUserName(final String userName) throws IllegalUserInfoException {
        return userManagement.validateNewUser(userName);
    }
}