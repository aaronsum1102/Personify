package com.Personify.controller;

import com.Personify.base.*;
import com.Personify.integration.TaskInfo;

import java.util.List;

public class Controller {
    private final UserManagement userManagement;
    private TaskCollection tasks;
    private Motivation motivationalQuotes;

    public Controller() {
        userManagement = new UserManagement();
    }

    public void afterLogIn(final String userProfileName) {
        motivationalQuotes = new Motivation(userProfileName);
        tasks = new TaskCollection(motivationalQuotes, userProfileName);
        readTaskDataToSystem();
    }

    public int getTasksSize() {
        return tasks.getTasksSize();
    }

    public List<Task> getAllTasks() {
        return tasks.getAllTasks();
    }

    public List<Task> getAllWorkTasks() {
        return tasks.getWorkTask();
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
        ((WorkTask) task).addCollaborators(collaborator);
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

    public String getCollaboratorsForDisplay(final int index) {
        return tasks.getCollaboratorsForDisplay(index);
    }

    public int getCollaboratorsSize(final int index) {
        return tasks.getCollaboratorsSize(index);
    }

    public void deleteCollaborator(final int taskIndex, final int collaboratorIndex) {
        tasks.deleteSpecificCollaborator(taskIndex, collaboratorIndex);
    }

    public void setAttribute(final int index, final String newInfo) {
        tasks.setAttribute(index, newInfo);
    }

    public void setRemarks(final int index, final String remarks) {
        tasks.setRemarks(index, remarks);
    }

    private void readTaskDataToSystem() {
        tasks.readTasksToSystem();
    }

    public void writeTaskDataToSystem(final String userName) {
        tasks.writeTasksToFile(userName);
    }

    public boolean removeAllTasks() {
        return tasks.removeAllTasks();
    }

    public void deleteSpecificTask(int index) {
        tasks.deleteSpecificTask(index);
    }

    public void deleteTasksThatWereDone(final List<Task> filteredTasks) {
        tasks.deleteTasksThatWereDone(filteredTasks);
    }

    public boolean userInfoValidation(final String userName, final String password) {
        return userManagement.userInfoValidation(userName, password);
    }

    public void saveUserProfile() {
        userManagement.saveUserProfile();
    }

    public void saveUserMotivationalQuotes(final String userName){
        motivationalQuotes.writeMotivationalQuoteToFIle(userName);
    }

    public void createUser(final String userName, final String password) throws IllegalUserInfoException {
        userManagement.createUser(userName, password);
    }

    public boolean validateNewUserName(final String userName) throws IllegalUserInfoException {
        return userManagement.validateNewUser(userName);
    }

    public void editUserName(final String currentUserName, final String newUserName) {
        userManagement.editUserName(currentUserName, newUserName);
    }

    public void editPassword(final String currentUserName, final String currentPassword, final String newPassword) throws IllegalUserInfoException {
        userManagement.editPassword(currentUserName, currentPassword, newPassword);
    }

    public String showAllMotivationalQuote(){
        return motivationalQuotes.toString();
    }

    public void addMotivationalQuote(final String quote) {
        motivationalQuotes.addQuote(quote);
    }

    public int getMotivationalQuotesSize() {
        return motivationalQuotes.getQuotes().size();
    }

    public void deleteSpecificQuote(final int index) {
        motivationalQuotes.removeQuote(index);
    }

    public void deleteAllQuotes() {
        motivationalQuotes.removeAllQuote();
    }
}