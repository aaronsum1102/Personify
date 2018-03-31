package com.Personify.controller;

import com.Personify.base.*;
import com.Personify.exception.IllegalUserInfoException;
import com.Personify.integration.TaskInfo;

import java.util.List;

/**
 * Responsible to interact with {@link com.Personify.base} package and {@link com.Personify.textView} for overall
 * functionality of the program.
 */
public class Controller {
    private final UserManagement userManagement;
    private TaskCollection tasks;
    private Motivation motivationalQuotes;

    /**
     * Instantiate a controller object.
     */
    public Controller() {
        userManagement = new UserManagement();
    }

    /**
     * Instantiate {@link Motivation} object, {@link TaskCollection} object and read all task data from task data
     * file specific to the user of the current session into system.
     *
     * @param userProfileName username of the user in current session.
     */
    public void afterLogIn(final String userProfileName) {
        motivationalQuotes = new Motivation(userProfileName);
        tasks = new TaskCollection(motivationalQuotes, userProfileName);
    }

    /**
     * Call {@link TaskCollection#getAll()} method to get a collection of all the <code>Task</code> object.
     *
     * @return a collection of all the <code>Task</code> object.
     */
    public List<Task> getAllTasks() {
        return tasks.getAll();
    }

    /**
     * Call {@link TaskCollection#getWork()} method to get a collection of all the <code>WorkTask</code> object.
     *
     * @return a collection of all the <code>WorkTask</code> object.
     */
    public List<Task> getAllWorkTasks() {
        return tasks.getWork();
    }

    /**
     * Call {@link TaskCollection#getSummary()} method to get a summary of all the <code>Task</code> object in
     * <code>TaskCollection</code> object.
     *
     * @return a summary of all the <code>Task</code> object in <code>TaskCollection</code> object as a collection of
     * <code>String</code>.
     */
    public List<String> getTasksSummary() {
        return tasks.getSummary();
    }

    /**
     * Call {@link TaskCollection#selectByStatusAndType(String, String)} method to get a collection of
     * <code>Task</code> object with the specific status and task type.
     *
     * @param taskType specific task type of an object in either <code>WorkTask</code> or <code>PersonalTask</code>.
     * @param status   Specific status of an object.
     * @return a collection of <code>Task</code> objects with the specific status and task type.
     */
    public List<Task> getTasksWithSpecificStatus(final String taskType, final String status) {
        return tasks.selectByStatusAndType(taskType, status);
    }

    /**
     * Call {@link TaskCollection#selectTaskToDoByType(String)} method to filter out a collection of
     * <code>Task</code> object with the specific task type from a collection of <code>Task</code> object.
     *
     * @param taskType specific task type of an object in either <code>WorkTask</code> or <code>PersonalTask</code>.
     * @return a collection of <code>Task</code> objects with the specific task type.
     */
    public List<Task> getTasksToComplete(final String taskType) {
        return tasks.selectTaskToDoByType(taskType);
    }

    /**
     * Add a new <code>PersonalTask</code> object into collection and provide a summary of the object added.
     *
     * @param TaskInfo {@link TaskInfo} object which contains detail information of task.
     * @param details  detailed description to be add to the <code>PersonalTask</code> object.
     * @return A summary of the information of the object added into collection.
     */
    public String addPersonalTaskAndGetSummary(final TaskInfo TaskInfo, final String details) {
        Task task = new PersonalTask(TaskInfo, motivationalQuotes, details);
        return tasks.addTaskWithSummary(task);
    }

    /**
     * Add a new <code>WorkTask</code> object into collection and provide a summary of the object added.
     *
     * @param TaskInfo     {@link TaskInfo} object which contains detail information of task.
     * @param collaborator collaborator to be added into a collection of collaborator for an <code>WorkTask</code>
     *                     object.
     * @return A summary of the information of the object added into collection.
     */
    public String addWorkTaskAndGetSummary(final TaskInfo TaskInfo, final String collaborator)
            throws IllegalArgumentException {
        Task task = new WorkTask(TaskInfo, motivationalQuotes);
        ((WorkTask) task).addCollaborators(collaborator);
        return tasks.addTaskWithSummary(task);
    }

    /**
     * Call {@link TaskCollection#isNameValid(String)} to check the validity of the specify task name.
     *
     * @param taskName task name to be checked for validity.
     * @return true if the task name if valid.
     */
    public boolean isTaskNameValid(final String taskName) {
        return tasks.isNameValid(taskName);
    }

    /**
     * Call {@link TaskCollection#setName(int, String)} method to edit the task name of the specify object with
     * the specify name.
     *
     * @param index   task number of n task object in <code>TaskCollection</code> object.
     * @param newName new name of the specify task object.
     */
    public void editTaskName(final int index, final String newName) {
        tasks.setName(index, newName);
    }

    /**
     * Call {@link TaskCollection#setDueDate(int, String)} method to edit the due date of the specify object with the
     * specify due date.
     *
     * @param index      task number of n task object in <code>TaskCollection</code> object.
     * @param newDueDate new due date of the specify object.
     */
    public void editDueDate(final int index, final String newDueDate) {
        tasks.setDueDate(index, newDueDate);
    }

    /**
     * Call {@link TaskCollection#setStatus(int, String)} method to edit the status of the specify object with the
     * specify status.
     *
     * @param index     task number of a task object in <code>TaskCollection</code> object.
     * @param newStatus new status of the specify object.
     */
    public void editTaskStatus(final int index, final String newStatus) {
        tasks.setStatus(index, newStatus);
    }

    /**
     * Call {@link TaskCollection#setPriority(int, String)} to edit the priority of the specify <code>Task</code>
     * object with the specify priority.
     *
     * @param index       task number of a task object in <code>TaskCollection</code> object.
     * @param newPriority new priority of the specify object.
     */
    public void editTaskPriority(final int index, final String newPriority) {
        tasks.setPriority(index, newPriority);
    }

    /**
     * Call {@link TaskCollection#showCollaborators(int)} method to get all the collaborators of the specific
     * <code>WorkTask</code> object
     *
     * @param index task number of a task object in <code>TaskCollection</code>collection.
     * @return all collaborators of a <code>WorkTask</code> object.
     */
    public String getCollaboratorsForDisplay(final int index) {
        return tasks.showCollaborators(index);
    }

    /**
     * Call {@link TaskCollection#getCollaboratorsSize(int)} method to get the number of collaborators of the
     * specific object in the collection of a <code>TaskCollection</code> object.
     *
     * @param index task number of a task object in the collection of a <code>TaskCollection</code> object.
     * @return number of collaborators of the specific object in in the collection of a <code>TaskCollection</code>
     * object.
     */
    public int getCollaboratorsSize(final int index) {
        return tasks.getCollaboratorsSize(index);
    }

    /**
     * Call {@link TaskCollection#clearCollaborator(int, int)} method to delete specific collaborator of a
     * <code>WorkTask</code> object.
     *
     * @param taskIndex         the task number of a task object in the collection of a <code>TaskCollection</code>
     *                          object.
     * @param collaboratorIndex the collaborator number of an object in the collection of a <code>WorkTask</code> object.
     */
    public void deleteCollaborator(final int taskIndex, final int collaboratorIndex) {
        tasks.clearCollaborator(taskIndex, collaboratorIndex);
    }

    /**
     * Call {@link TaskCollection#setAttribute(int, String)} to set details or add collaborator for a
     * {@link PersonalTask} object or {@link WorkTask} object respectively
     *
     * @param index   the task number of a task object in the collection of a <code>TaskCollection</code> object.
     * @param newInfo the information to set for specific in {@link TaskCollection}.
     */
    public void setAttribute(final int index, final String newInfo) {
        tasks.setAttribute(index, newInfo);
    }

    /**
     * Call {@link TaskCollection#setRemarks(int, String)} to set the remarks of an task object.
     *
     * @param index   the task number of a task object in the collection of a <code>TaskCollection</code> object.
     * @param remarks the remarks to be added for an task object.
     */
    public void setRemarks(final int index, final String remarks) {
        tasks.setRemarks(index, remarks);
    }

    /**
     * Call {@link TaskCollection#writeToFile(String)} method to write the task data of a specify username to file.
     *
     * @param userName username in current session.
     */
    public void writeTaskDataToSystem(final String userName) {
        tasks.writeToFile(userName);
    }

    /**
     * Call {@link TaskCollection#clear()} method to clear all task objects in the collection.
     *
     * @return true if the collection is empty after operation.
     */
    public boolean removeAllTasks() {
        return tasks.clear();
    }

    /**
     * Call {@link TaskCollection#remove(int)} to clear specific task object in the
     * collection.
     *
     * @param index the task number to be removed from collection.
     */
    public void deleteSpecificTask(int index) {
        tasks.remove(index);
    }

    /**
     * Call {@link TaskCollection#removeDoneTasks(List)} to clear all task objects with the
     * status of "done".
     *
     * @param filteredTasks the collection of tasks to to removed task object with status marked as "done".
     */
    public void deleteTasksThatWereDone(final List<Task> filteredTasks) {
        tasks.removeDoneTasks(filteredTasks);
    }

    /**
     * Call {@link UserManagement#userValidation(String, String)} method to validate the
     * specify password for the specify user match the record in system.
     *
     * @param userName username to be tested.
     * @param password password to be tested.
     * @return true if the password for the specific username match the record in system.
     */
    public boolean userInfoValidation(final String userName, final String password) {
        return userManagement.userValidation(userName, password);
    }

    /**
     * Call {@link UserManagement#saveProfile()} method to write the user information of the user in current
     * session to file.
     */
    public void saveUserProfile() {
        userManagement.saveProfile();
    }

    /**
     * Call {@link Motivation#writeMotivationalQuoteToFIle(String)} method to write all motivational quotes specific to
     * the user in current session to file.
     *
     * @param userName username of the user in current session.
     */
    public void saveUserMotivationalQuotes(final String userName) {
        motivationalQuotes.writeMotivationalQuoteToFIle(userName);
    }

    /**
     * Call {@link UserManagement#createUser(String, String)} method to instantiate new <code>User</code> object.
     *
     * @param userName username to use for instantiate of the new <code>User</code> object.
     * @param password password to use for instantiate of the new <code>User</code> object.
     * @throws IllegalUserInfoException if the specify password is invalid.
     */
    public void createUser(final String userName, final String password) throws IllegalUserInfoException {
        userManagement.createUser(userName, password);
    }

    /**
     * Call {@link UserManagement#validateNewUsername(String)} method to check if the specify username is valid.
     *
     * @param userName username to be checked.
     * @return true if the username is valid.
     * @throws IllegalUserInfoException if the username is invalid.
     */
    public boolean validateNewUserName(final String userName) throws IllegalUserInfoException {
        return userManagement.validateNewUsername(userName);
    }

    /**
     * Call {@link UserManagement#setUsername(String, String)} method to set username of the user in current session.
     *
     * @param currentUserName username of the user in current session.
     * @param newUserName     new username to be used for the user in current session.
     */
    public void editUserName(final String currentUserName, final String newUserName) throws IllegalUserInfoException {
        userManagement.setUsername(currentUserName, newUserName);
    }

    /**
     * Call {@link UserManagement#setPassword(String, String, String)} method to set password of the user in current
     * session.
     *
     * @param currentUserName username of the user in current session.
     * @param currentPassword existing password of the user in current session.
     * @param newPassword     new password to be used for the user in current session.
     * @throws IllegalUserInfoException if the new password is invalid.
     */
    public void editPassword(final String currentUserName, final String currentPassword, final String newPassword)
            throws IllegalUserInfoException {
        userManagement.setPassword(currentUserName, currentPassword, newPassword);
    }

    /**
     * Call {@link Motivation#toString()} method to get all the motivational quotes for the user in current session.
     *
     * @return a string of all motivation quotes for the user in current session.
     */
    public String showAllMotivationalQuote() {
        return motivationalQuotes.toString();
    }

    /**
     * Call {@link Motivation#addQuote(String)} to add the specify quote into the collection of motivation quote in a
     * <code>Motivation</code> object.
     *
     * @param quote quote to be added into the collection of <code>Motivation</code> object.
     */
    public void addMotivationalQuote(final String quote) {
        motivationalQuotes.addQuote(quote);
    }

    /**
     * Call {@link Motivation#getQuote()} and get the number of motivational quote in the collection of a
     * <code>Motivation</code> object.
     *
     * @return number of motivational quote in the collection of a <code>Motivation</code> object.
     */
    public int getMotivationalQuotesSize() {
        return motivationalQuotes.getQuotes().size();
    }

    /**
     * Call {@link Motivation#removeQuote(int)} to clear specific quote from the collection of a
     * <code>Motivation</code> object.
     *
     * @param index quote number of the specific quote in the collection of a <code>Motivation</code> object.
     */
    public void deleteSpecificQuote(final int index) {
        motivationalQuotes.removeQuote(index);
    }

    /**
     * Call{@link Motivation#removeAllQuote()} to clear all quotes from the collection of a <code>Motivation</code>
     * object.
     */
    public void deleteAllQuotes() {
        motivationalQuotes.removeAllQuote();
    }
}