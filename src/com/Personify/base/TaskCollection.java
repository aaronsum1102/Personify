package com.Personify.base;

import com.Personify.integration.SubTaskInfo;
import com.Personify.integration.TaskFileIO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Managing collection of all <code>Task</code> object. Main functions include adding new task to collection,
 * clear tasks, of filtering tasks based on request.
 */
public class TaskCollection {
    private final TaskFileIO taskDataIO;
    private final Motivation motivationalQuotes;
    private List<Task> tasks;
    private List<Task> workTasks;
    private List<Task> personalTasks;

    /**
     * Instantiate a <code>TaskCollection</code> object for managing collection of tasks and instantiate
     * <code>TaskFileIO</code> object for reading task data for specific user into system.
     *
     * @param motivationalQuotes {@link Motivation} object which hold a collections of motivational quotes.
     * @param userProfile        username of the use in current session.
     */
    public TaskCollection(final Motivation motivationalQuotes, final String userProfile) {
        tasks = new ArrayList<>();
        this.motivationalQuotes = motivationalQuotes;
        taskDataIO = new TaskFileIO(userProfile);
        readFromFile();
    }

    /**
     * Provide a summary of the specify task object added into collection and filter into different collections
     * according to the type of the task object.
     *
     * @param task task object to be added into collection.
     * @return a summary of the specify task added into collection.
     */
    public String addTaskWithSummary(final Task task) {
        tasks.add(task);
        updateWorkTasksAndPersonalTasks();
        return task.getSummary();
    }

    /**
     * Determine if the specify task name is not empty string and not exist in collection.
     *
     * @param taskName task name for validation.
     * @return true if the specify task name is valid.
     */
    public boolean isNameValid(final String taskName) {
        if (Task.isNameNotEmptyString(taskName) && isUniqueName(taskName)) {
            return true;
        } else if (!isUniqueName(taskName)) {
            throw new IllegalArgumentException("Warning: You have the same task already. Please give me a new name.");
        } else {
            throw new IllegalArgumentException("Warning: Hey, you didn't type in anything!");
        }
    }

    private boolean isUniqueName(final String taskName) {
        return tasks.stream().noneMatch(task -> task.getName().toLowerCase().equals(taskName.toLowerCase()));
    }

    private void sortByDueDate(final List<Task> tasks) {
        tasks.sort(Comparator.comparingLong(firstTask -> firstTask.getReminderObject().findDaysLeft()));
        Collections.reverse(tasks);
    }

    private void updateWorkTasksAndPersonalTasks() {
        workTasks = selectByType("WorkTask");
        personalTasks = selectByType("PersonalTask");
    }

    private List<Task> selectByType(final String TaskClassName) {
        return tasks.stream()
                .filter(task -> task.getClass().getSimpleName().equals(TaskClassName))
                .collect(Collectors.toList());
    }

    /**
     * Provide a collection of task objects sorted by days left to due date.
     *
     * @return a collection of <code>Task</code> objects.
     */
    public List<Task> getAll() {
        sortByDueDate(tasks);
        return tasks;
    }

    /**
     * Provide a collection of task objects with the type as <code>WordTask</code> sorted by days left to due date.
     *
     * @return a collection of <code>Task</code> objects.
     */
    public List<Task> getWork() {
        sortByDueDate(workTasks);
        return workTasks;
    }

    /**
     * Provide a collection of task objects with the type as <code>PersonalTask</code> sorted by days left to due date.
     *
     * @return a collection of <code>PersonalTask</code> objects.
     */
    public List<Task> getPersonal() {
        sortByDueDate(personalTasks);
        return personalTasks;
    }

    private List<Task> selectByStatus(final List<Task> tasks, final String status) {
        sortByDueDate(tasks);
        return tasks.stream()
                .filter(task -> task.getStatusObject().getStatus().equals(status))
                .collect(Collectors.toList());
    }

    /**
     * Filtering collection of task objects according to the specify task type and status.
     *
     * @param taskType type of the task as either <code>WorkTask</code> or <code>PersonalTask</code>
     * @param status   status of to apply as filter condition.
     * @return a list of <code>Task</code> object based on specify conditions.
     */
    public List<Task> selectByStatusAndType(final String taskType, final String status) {
        switch (taskType) {
            case "WorkTask":
                return selectByStatus(workTasks, status);
            case "PersonalTask":
                return selectByStatus(personalTasks, status);
        }
        return selectByStatus(tasks, status);
    }

    private List<Task> selectTasksToDO(final List<Task> tasks) {
        sortByDueDate(tasks);
        return tasks.stream()
                .filter(task -> !(task.getStatusObject().getStatus().equals("done")))
                .collect(Collectors.toList());
    }

    /**
     * Filtering collection of task objects to be done according to the specify task type.
     *
     * @param taskType type of the task as either <code>WorkTask</code> or <code>PersonalTask</code>
     * @return a collection of  <code>Task</code> object to be completed.
     */
    public List<Task> selectTaskToDoByType(final String taskType) {
        switch (taskType) {
            case "WorkTask":
                return selectTasksToDO(workTasks);
            case "PersonalTask":
                return selectTasksToDO(personalTasks);
        }
        return selectTasksToDO(tasks);
    }

    /**
     * Set the task name of a <code>Task</code> object according to the specify name.
     *
     * @param taskNumber task number the task to be edited.
     * @param newName    new name of the task.
     */
    public void setName(final int taskNumber, final String newName) {
        Task taskToEdit = tasks.get(taskNumber - 1);
        taskToEdit.changeName(newName);
    }

    /**
     * Set the due date of a <code>Task</code> object according to the specify due date.
     *
     * @param taskNumber task number the task to be edited.
     * @param newDueDate new due date of the task object.
     * @throws IllegalArgumentException if the specify due date is empty or in incorrect format or is the same as the
     *                                  due date of the object.
     */
    public void setDueDate(final int taskNumber, final String newDueDate) throws IllegalArgumentException {
        Task taskToEdit = tasks.get(taskNumber - 1);
        taskToEdit.changeDueDate(newDueDate);
    }

    /**
     * Set the status of <code>Task</code> object according to specify status.
     *
     * @param taskNumber task number of the task to be edited.
     * @param newStatus  new status of the task object.
     */
    public void setStatus(final int taskNumber, final String newStatus) {
        Task taskToEdit = tasks.get(taskNumber - 1);
        taskToEdit.setStatus(newStatus);
    }

    /**
     * Set the priority of <code>Task</code> object according to specify priority.
     *
     * @param taskNumber  task number of the task to be edited.
     * @param newPriority new priority of the task.
     */
    public void setPriority(final int taskNumber, final String newPriority) {
        Task taskToEdit = tasks.get(taskNumber - 1);
        taskToEdit.setPriority(newPriority);
    }

    /**
     * Provide the collaborators of <code>WorkTask</code> objects.
     *
     * @param taskNumber task number of the task to show all collaborators.
     * @return a string with all the collaborators name.
     */
    public String showCollaborators(final int taskNumber) {
        return ((WorkTask) workTasks.get(taskNumber - 1)).getCollaboratorsForDisplay();
    }

    /**
     * Provide the number of collaborator of a specify <code>WorkTask</code> object.
     *
     * @param taskNumber task number of the task to show the number of collaborator.
     * @return number of collaborator for the object.
     */
    public int getCollaboratorsSize(final int taskNumber) {
        return ((WorkTask) workTasks.get(taskNumber - 1)).getCollaborators().size();
    }

    /**
     * Remove specific collaborator of specific <code>Task</code> object
     *
     * @param taskNumber         task number of the task to clear specify collaborator.
     * @param collaboratorIndex collaborator number in collection to clear for the specific object.
     */
    public void clearCollaborator(final int taskNumber, final int collaboratorIndex) {
        ((WorkTask) workTasks.get(taskNumber - 1)).getCollaborators().remove(collaboratorIndex - 1);
    }

    /**
     * Set the attribute which is specific to the task type. If task type is <code>WorkTask</code> it will set the
     * details of task type is <code>PersonalTask</code> it will add new collaborator for the object.
     *
     * @param taskNumber task number of the task to add new attribute information.
     * @param newInfo   the specific information for a task object.
     */
    public void setAttribute(final int taskNumber, final String newInfo) {
        Task taskToEdit = tasks.get(taskNumber - 1);
        String className = taskToEdit.getClass().getSimpleName();
        if (newInfo.isEmpty()) {
            throw new IllegalArgumentException("Warning: You didn't type in anything!");
        } else {
            if (className.equals("WorkTask")) {
                ((WorkTask) taskToEdit).addCollaborators(newInfo);
            } else {
                ((PersonalTask) taskToEdit).setDetails(newInfo);
            }
        }
    }

    /**
     * Set the remarks of the a task object.
     *
     * @param taskNumber   task number to set the remarks.
     * @param remarks new remarks to set for the specify object.
     */
    public void setRemarks(final int taskNumber, final String remarks) {
        Task taskToEdit = tasks.get(taskNumber - 1);
        taskToEdit.setRemarks(remarks);
    }

    private Task addPersonalTask(final SubTaskInfo taskData) {
        return new PersonalTask(taskData.getTaskInfo(), motivationalQuotes, taskData.getTypeSpecificAttribute());
    }

    private Task addWorkTask(final SubTaskInfo taskData) {
        Task task = new WorkTask(taskData.getTaskInfo(), motivationalQuotes);
        List<String> collaborators = new ArrayList<>();
        if (!(taskData.getTypeSpecificAttribute().isEmpty())) {
            Scanner tokenizer = new Scanner(taskData.getTypeSpecificAttribute());
            tokenizer.useDelimiter(", ");
            collaborators.add(tokenizer.next());
            collaborators.forEach(((WorkTask) task)::addCollaborators);
        }
        return task;
    }

    private List<Task> readData() {
        List<Task> tasksFromFile = new ArrayList<>();
        List<SubTaskInfo> tasksToRead = taskDataIO.readTasksDataFromDataFile();
        if (!tasksToRead.isEmpty()) {
            tasksToRead.forEach(taskData -> {
                String className = taskData.getClassName();
                switch (className) {
                    case "PersonalTask":
                        tasksFromFile.add(addPersonalTask(taskData));
                        break;
                    case "WorkTask":
                        tasksFromFile.add(addWorkTask(taskData));
                        break;
                }
            });
        }
        return tasksFromFile;
    }

    private void readFromFile() {
        tasks = readData();
        if (!tasks.isEmpty()) {
            workTasks = selectByType("WorkTask");
            personalTasks = selectByType("PersonalTask");
        } else {
            workTasks = new ArrayList<>();
            personalTasks = new ArrayList<>();
        }
    }

    /**
     * Write task data to file for the user in current session.
     *
     * @param userName username of the user in current session.
     */
    public void writeToFile(final String userName) {
        taskDataIO.writeTasksToFile(tasks, userName);
    }

    /**
     * Empty the task collection.
     *
     * @return true if the tasks collection is empty.
     */
    public boolean clear() {
        if (!tasks.isEmpty()) {
            tasks.clear();
            workTasks.clear();
            personalTasks.clear();
            return true;
        }
        return false;
    }

    /**
     * Remove specific task object from collection.
     *
     * @param index task number to be removed from collection.
     */
    public void remove(final int index) {
        tasks.remove(index - 1);
        updateWorkTasksAndPersonalTasks();
    }

    /**
     * Remove all tasks object with status as "done" in the collection.
     *
     * @param tasks collection of task to be modified.
     */
    public void removeDoneTasks(final List<Task> tasks) {
        this.tasks.removeAll(tasks);
        updateWorkTasksAndPersonalTasks();
    }

    /**
     * Provide a summary on the task collection. It provide information on the number of <code>Task</code>  object in
     * collection, number of <code>WorkTask</code> object, number of <code>PersonalTask</code> object, number of tasks
     * that are overdue or done for <code>WorkTask</code> object and <code>PersonalTask</code> object respectively.
     *
     * @return a summary of the existing task collection.
     */
    public List<String> getSummary() {
        List<String> summary = new ArrayList<>();
        summary.add(String.format("%-48s%-2s%d\n", "Total number of task", ":", tasks.size()));
        summary.add(String.format("%-48s%-2s%d", "Total number of personal task", ":", personalTasks.size()));
        summary.add(String.format("%-48s%-2s%d", "Total number of personal task that are overdue", ":",
                selectByStatus(personalTasks, "overdue").size()));
        summary.add(String.format("%-48s%-2s%d\n", "Total number of personal task that are done", ":",
                selectByStatus(personalTasks, "done").size()));
        summary.add(String.format("%-48s%-2s%d", "Total number of work of task", ":", workTasks.size()));
        summary.add(String.format("%-48s%-2s%d", "Total number of work task that are overdue", ":",
                selectByStatus(workTasks, "overdue").size()));
        summary.add(String.format("%-48s%-2s%d\n", "Total number of work task that are done", ":",
                selectByStatus(workTasks, "done").size()));
        return summary;
    }
}
