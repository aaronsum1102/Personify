package com.Personify.base;

import com.Personify.integration.SubTaskInfo;
import com.Personify.integration.TaskFileIO;

import java.util.*;
import java.util.stream.Collectors;

public class TaskCollection {
    private final TaskFileIO taskDataIO;
    private final Motivation motivationalQuotes;
    private List<Task> tasks;

    public TaskCollection(final Motivation motivationalQuotes, final String userProfile) {
        tasks = new ArrayList<>();
        this.motivationalQuotes = motivationalQuotes;
        taskDataIO = new TaskFileIO(userProfile);
    }

    public int getTasksSize() {
        return tasks.size();
    }

    public String getAddTaskSummary(final Task task) {
        tasks.add(task);
        return task.getSummary();
    }

    public boolean isTaskNameValid(final String taskName) {
        if (Task.isNameNotEmptyString(taskName) && isNotRepeatedTaskInCollection(taskName)) {
            return true;
        } else if (!isNotRepeatedTaskInCollection(taskName)) {
            throw new IllegalArgumentException("Warning: You have the same task already. Please give me a new name.");
        } else {
            throw new IllegalArgumentException("Warning: Hey, you didn't type in anything!");
        }
    }

    private boolean isNotRepeatedTaskInCollection(final String taskName) {
        return tasks.stream().noneMatch(task -> task.getName().toLowerCase().equals(taskName.toLowerCase()));
    }

    private void sortTasksByDueDate(List<Task> tasks) {
        tasks.sort(Comparator.comparingLong(firstTask -> firstTask.getReminderObject().findDaysLeft()));
        Collections.reverse(tasks);
    }


    public List<Task> getAllTasks() {
        sortTasksByDueDate(tasks);
        return tasks;
    }

    public List<Task> getTasksWithSpecificStatus(final String status) {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getStatusObject().getStatus().equals(status))
                .collect(Collectors.toList());
        sortTasksByDueDate(filteredTasks);
        return filteredTasks;
    }

    public List<Task> getTasksToComplete() {
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> !(task.getStatusObject().getStatus().equals("done")))
                .collect(Collectors.toList());
        sortTasksByDueDate(filteredTasks);
        return filteredTasks;
    }

    public void editTaskName(final int taskIndex, final String newName) {
        Task taskToEdit = tasks.get(taskIndex - 1);
        taskToEdit.changeName(newName);
    }

    public void editDueDate(final int taskIndex, final String newDueDate) throws IllegalArgumentException {
        Task taskToEdit = tasks.get(taskIndex - 1);
        taskToEdit.changeDueDate(newDueDate);
    }

    public void editStatus(final int taskIndex, final String newStatus) {
        Task taskToEdit = tasks.get(taskIndex - 1);
        taskToEdit.setStatus(newStatus);
    }

    public void editPriority(final int taskIndex, final String newPriority) {
        Task taskToEdit = tasks.get(taskIndex - 1);
        taskToEdit.setPriority(newPriority);
    }

    public void setAttribute(final int taskIndex, final String newInfo) {
        Task taskToEdit = tasks.get(taskIndex - 1);
        String className = taskToEdit.getClass().getSimpleName();
        if (className.equals("WorkTask")) {
            ((WorkTask) taskToEdit).addCollaborators(newInfo);
        } else {
            ((PersonalTask) taskToEdit).setDetails(newInfo);
        }
    }

    public void setRemarks(final int index, final String remarks) {
        Task taskToEdit = tasks.get(index - 1);
        taskToEdit.setRemarks(remarks);
    }

    private List<Task> readTasksFromFile() {
        List<Task> tasksFromFile = new ArrayList<>();
        List<SubTaskInfo> tasksToRead = taskDataIO.readTasksDataFromDataFile();
        if (!tasksToRead.isEmpty()) {
            tasksToRead.forEach(taskData -> {
                String className = taskData.getClassName();
                switch (className) {
                    case "PersonalTask":
                        Task task = new PersonalTask(taskData.getTaskInfo(), motivationalQuotes, taskData.getTypeSpecificAttribute());
                        tasksFromFile.add(task);
                        break;
                    case "WorkTask":
                        task = new WorkTask(taskData.getTaskInfo(), motivationalQuotes);
                        List<String> collaborators = new ArrayList<>();
                        if (!(taskData.getTypeSpecificAttribute().isEmpty())) {
                            Scanner tokenizer = new Scanner(taskData.getTypeSpecificAttribute());
                            tokenizer.useDelimiter(", ");
                            collaborators.add(tokenizer.next());
                            collaborators.forEach(((WorkTask) task)::addCollaborators);
                        }
                        tasksFromFile.add(task);
                        break;
                }
            });
        }
        return tasksFromFile;
    }

    public void readTasksToSystem() {
        tasks = readTasksFromFile();
    }

    public void writeTasksToFile(final String userName) {
        taskDataIO.writeTasksToFile(tasks, userName);
    }

    public boolean removeAllTasks() {
        if (!tasks.isEmpty()) {
            tasks.clear();
            return true;
        }
        return false;
    }

    public void deleteSpecificTask(final int index) {
        tasks.remove(index - 1);
    }

    public void deleteTasksThatWereDone(final List<Task> tasks) {
        this.tasks.removeAll(tasks);
    }
}
