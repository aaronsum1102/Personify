package com.Personify.integration;

import com.Personify.base.PersonalTask;
import com.Personify.base.Task;
import com.Personify.base.WorkTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Provide method to read task data from file and write task data to file.
 */
public class TaskFileIO extends FileIO {
    private final Path path;

    /**
     * Construct <code>TaskFileIO</code> object to read and write task data of a specific user in current session from
     * or to file.
     *
     * @param userProfile username of the user in current session.
     */
    public TaskFileIO(final String userProfile) {
        path = Paths.get("src/data", String.format("%s_tasksData.csv", userProfile));
    }

    private List<String> tokenizeTasksDetails(List<String> tasks) {
        List<String> tasksInfo = new ArrayList<>();
        for (String task : tasks) {
            Scanner scanner = new Scanner(task);
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                tasksInfo.add(scanner.next());
            }
            scanner.close();
        }
        return tasksInfo;
    }

    private List<SubTaskInfo> readEachTaskAndPackToDTOCollection(final List<String> tokenizeData) {
        List<SubTaskInfo> tasksData = new ArrayList<>();
        String className;
        String taskName;
        String dueDate;
        String status;
        String priority;
        String remarks;
        String typeSpecificAttribute = "";
        Iterator<String> it = tokenizeData.iterator();
        while (it.hasNext()) {
            className = it.next();
            taskName = it.next();
            dueDate = it.next();
            status = it.next();
            priority = it.next();
            remarks = it.next();
            String next = it.next();
            if (!(next.equals("-"))) {
                it.next();
                typeSpecificAttribute = next;
            }
            TaskInfo taskInfo = new TaskInfo(taskName, dueDate, status, priority, remarks);
            SubTaskInfo info = new SubTaskInfo(className, taskInfo, typeSpecificAttribute);
            tasksData.add(info);
            typeSpecificAttribute = "";
        }
        return tasksData;
    }

    private List<SubTaskInfo> readTaskToTaskInfoCollection() {
        List<String> eachTask = readEachLineOfFile(path);
        List<String> tokenizeTaskData = tokenizeTasksDetails(eachTask);
        return readEachTaskAndPackToDTOCollection(tokenizeTaskData);
    }

    private boolean isTaskDataFileExistsAndReadable() {
        return Files.isReadable(path);
    }

    /**
     * Read task data from of a user to system.
     *
     * @return a collection of <code>SubTaskInfo</code> objects.
     */
    public List<SubTaskInfo> readTasksDataFromDataFile() {
        List<SubTaskInfo> taskData = new ArrayList<>();
        if (isTaskDataFileExistsAndReadable()) {
            taskData = readTaskToTaskInfoCollection();
        }
        return taskData;
    }

    private List<SubTaskInfo> prepareTasksDataForArchive(List<Task> tasks) {
        List<SubTaskInfo> tasksForArchive = new ArrayList<>();
        tasks.forEach(task -> {
            String className = task.getClass().getSimpleName();
            String name = task.getName();
            String dueDate = task.getDueDate().toString();
            String status = task.getStatusObject().getStatus();
            String priority = task.getPriorityObject().getPriority();
            String remarks = task.getRemarks();
            String typeSpecificAttribute = "";
            switch (className) {
                case "PersonalTask":
                    typeSpecificAttribute = ((PersonalTask) task).getDetails();
                    break;
                case "WorkTask":
                    typeSpecificAttribute = ((WorkTask) task).getCollaboratorsAsString();
            }
            TaskInfo taskInfo = new TaskInfo(name, dueDate, status, priority, remarks);
            tasksForArchive.add(new SubTaskInfo(className, taskInfo, typeSpecificAttribute));
        });
        return tasksForArchive;
    }

    private List<String> formattingTaskDataForArchive(List<SubTaskInfo> tasksToArchive) {
        List<String> dataToWriteToFile = new ArrayList<>();
        tasksToArchive.forEach(innerList -> dataToWriteToFile.add((innerList.toString() + ";-;")));
        return dataToWriteToFile;
    }

    private void writeToFile(final List<String> tasksToWriteToFile, final String userName) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("IO error while manipulating file.");
        }
        final Path newFile = Paths.get("src/data", String.format("%s_tasksData.csv", userName));
        FileIO writeFile = new FileIO();
        writeFile.writeTaskToFile(newFile, tasksToWriteToFile);
    }

    /**
     * Write user specific task data to file.
     *
     * @param tasks    collection of <code>Task</code> object for specific user.
     * @param userName username of the user in current session.
     */
    public void writeTasksToFile(final List<Task> tasks, final String userName) {
        List<SubTaskInfo> tasksToArchive = prepareTasksDataForArchive(tasks);
        List<String> tasksToWriteToFile = formattingTaskDataForArchive(tasksToArchive);
        writeToFile(tasksToWriteToFile, userName);
    }
}
