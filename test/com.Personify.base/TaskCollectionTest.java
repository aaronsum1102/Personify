package com.Personify.base;

import com.Personify.integration.TaskInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskCollectionTest {
    private String username;
    private Motivation motivationalQuotes;
    private Task task;
    private TaskCollection tasks;

    @BeforeEach
    void setUp() {
        username = "abc";
        motivationalQuotes = new Motivation(username);
        tasks = new TaskCollection(motivationalQuotes, username);
        TaskInfo taskInfo = new TaskInfo("write report", LocalDate.now().plusDays(1).toString(), "", "", "");
        Task task = new WorkTask(taskInfo, motivationalQuotes);
        ((WorkTask) task).addCollaborators(("aaron"));
        tasks.addTaskWithSummary(task);
        taskInfo = new TaskInfo("baking", LocalDate.now().plusDays(2).toString(), "in progress", "", "");
        task = new PersonalTask(taskInfo, motivationalQuotes, "");
        tasks.addTaskWithSummary(task);
        taskInfo = new TaskInfo("market research", LocalDate.now().plusDays(3).toString(), "done", "", "");
        task = new WorkTask(taskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(task);
        taskInfo = new TaskInfo("pay bill", LocalDate.now().plusDays(4).toString(), "overdue", "", "");
        task = new PersonalTask(taskInfo, motivationalQuotes, "");
        tasks.addTaskWithSummary(task);
    }

    @AfterEach
    void tearDown() throws IOException {
        tasks = null;
        Path path = Paths.get("src/data", String.format("%s_tasksData.csv", username));
        Files.deleteIfExists(path);
    }

    @Test
    void testReadTaskRecordForNewUser() {
        assertEquals(4, tasks.getAll().size());
    }

    @Test
    void testReadTaskRecordForExistingUser() {
        TaskInfo taskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        task = new WorkTask(taskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(task);
        assertEquals(5, tasks.getAll().size());
        tasks.writeToFile(username);
        tasks = new TaskCollection(motivationalQuotes, username);
        assertEquals(5, tasks.getAll().size());
    }

    @Test
    void testGetTaskSizeOfACollectionWithOneTask() {
        TaskInfo taskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        task = new WorkTask(taskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(task);
        assertEquals(5, tasks.getAll().size());
    }

    @Test
    void testGetTaskSizeOfACollectionWithNoTask() {
        tasks.getAll().clear();
        assertEquals(0, tasks.getAll().size());
    }

    @Test
    void testAddTaskOfPersonalTaskType() {
        TaskInfo taskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        task = new PersonalTask(taskInfo, motivationalQuotes, "weekly");
        tasks.addTaskWithSummary(task);
        assertEquals(3, tasks.getPersonal().size());
        assertEquals(2, tasks.getWork().size());
    }

    @Test
    void testAddTaskOfWorkTaskType() {
        TaskInfo taskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        task = new WorkTask(taskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(task);
        assertEquals(3, tasks.getWork().size());
        assertEquals(2, tasks.getPersonal().size());
    }

    @Test
    void testAddWorkAndPersonalTaskType() {
        TaskInfo workTaskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        Task workTask = new WorkTask(workTaskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(workTask);
        TaskInfo PersonalTaskInfo = new TaskInfo("shopping", "2018-03-10", "in progress", "medium", "");
        Task personalTask = new PersonalTask(PersonalTaskInfo, motivationalQuotes, "weekly");
        tasks.addTaskWithSummary(personalTask);
        assertEquals(3, tasks.getWork().size());
        assertEquals(3, tasks.getPersonal().size());
    }

    @Test
    void testValidTaskName() {
        String newTaskName = "shopping";
        assertEquals(true, tasks.isNameValid(newTaskName));
    }

    @Test
    void testInvalidTaskNameWithRepeatedTaskName() {
        TaskInfo workTaskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        Task workTask = new WorkTask(workTaskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(workTask);
        String newTaskName = "laundry";
        assertThrows(IllegalArgumentException.class, () -> tasks.isNameValid(newTaskName));
    }

    @Test
    void testInvalidTaskNameWithEmptyString() {
        String newTaskName = "";
        assertThrows(IllegalArgumentException.class, () -> tasks.isNameValid(newTaskName));
    }

    @Test
    void testGetAllTasksSortedByDyeDate() {
        tasks.getAll().clear();
        TaskInfo workTaskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        Task workTask = new WorkTask(workTaskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(workTask);
        TaskInfo PersonalTaskInfo = new TaskInfo("shopping", "2018-03-20", "in progress", "medium", "");
        Task personalTask = new PersonalTask(PersonalTaskInfo, motivationalQuotes, "weekly");
        tasks.addTaskWithSummary(personalTask);
        assertEquals("shopping", tasks.getAll().get(0).getName());
        assertEquals("laundry", tasks.getAll().get(1).getName());
    }

    @Test
    void testWorkTaskSortedByDueDate() {
        tasks.getAll().clear();
        TaskInfo firstTaskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        Task firstTask = new WorkTask(firstTaskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(firstTask);
        TaskInfo secondTaskInfo = new TaskInfo("baking", "2018-03-15", "in progress", "low", "");
        Task secondTask = new WorkTask(secondTaskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(secondTask);
        tasks.getAll();
        assertEquals("baking", tasks.getAll().get(0).getName());
        assertEquals("laundry", tasks.getAll().get(1).getName());
    }

    @Test
    void testPersonalTaskSortedByDueDate() {
        tasks.getAll().clear();
        TaskInfo firstTaskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        Task firstTask = new WorkTask(firstTaskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(firstTask);
        TaskInfo secondTaskInfo = new TaskInfo("baking", "2018-03-15", "in progress", "low", "");
        Task secondTask = new PersonalTask(secondTaskInfo, motivationalQuotes, "my hobby");
        tasks.addTaskWithSummary(secondTask);
        tasks.getAll();
        assertEquals("baking", tasks.getAll().get(0).getName());
        assertEquals("laundry", tasks.getAll().get(1).getName());
    }

    @Test
    void testFilterForWorkTaskToDo() {
        List<Task> filteredTasks = tasks.selectByStatusAndType("WorkTask", "to do");
        assertEquals(1, filteredTasks.size());
        assertEquals("write report", filteredTasks.get(0).getName());
    }

    @Test
    void testFilterForWorkTaskThatAreDone() {
        List<Task> filteredTasks = tasks.selectByStatusAndType("WorkTask", "done");
        assertEquals(1, filteredTasks.size());
        assertEquals("market research", filteredTasks.get(0).getName());
    }

    @Test
    void testFilterForPersonalTaskInProgress() {
        List<Task> filteredTasks = tasks.selectByStatusAndType("PersonalTask", "in progress");
        assertEquals(1, filteredTasks.size());
        assertEquals("baking", filteredTasks.get(0).getName());
    }

    @Test
    void testFilterForPersonalTaskThatAreOverdue() {
        List<Task> filteredTasks = tasks.selectByStatusAndType("PersonalTask", "overdue");
        assertEquals(1, filteredTasks.size());
        assertEquals("pay bill", filteredTasks.get(0).getName());
    }

    @Test
    void testPersonalTasksThatAreNotDone() {
        List<Task> filteredTasks = tasks.selectTaskToDoByType("PersonalTask");
        assertEquals(2, filteredTasks.size());
    }

    @Test
    void testWorkTasksThatAreNotDone() {
        List<Task> filteredTasks = tasks.selectTaskToDoByType("WorkTask");
        assertEquals(1, filteredTasks.size());
    }

    @Test
    void testEditTaskName() {
        String newName = "prepare presentation slides";
        int index = 4;
        tasks.setName(index, newName);
        assertEquals(newName, tasks.getAll().get(0).getName());
    }

    @Test
    void testEditFileNameWithEmptyTaskName() {
        String newName = "";
        int index = 4;
        tasks.setName(index, newName);
        assertEquals("pay bill", tasks.getAll().get(0).getName());
    }

    @Test
    void testEditFileNameWithRepeatedName() {
        String newName = "pay bill";
        int index = 4;
        tasks.setName(index, newName);
        assertEquals("pay bill", tasks.getAll().get(0).getName());
    }

    @Test
    void testEditDueDate() {
        String newDueDate = LocalDate.now().minusDays(10).toString();
        int taskNumber = 1;
        tasks.setDueDate(taskNumber, newDueDate);
        String actualDueDate = tasks.getAll().get(3).getDueDate().toString();
        assertEquals(newDueDate, actualDueDate);
    }

    @Test
    void testEditStatus() {
        int taskNumber = 1;
        final int taskIndex = 3;
        String newStatus = "done";
        String expectedTaskName = "write report";
        tasks.setStatus(taskNumber, newStatus);
        Task taskEdited = tasks.getAll().get(taskIndex);
        String actualStatus = taskEdited.getStatusObject().getStatus();
        String actualTaskName = taskEdited.getName();
        assertEquals(expectedTaskName, actualTaskName);
        assertEquals(newStatus, actualStatus);
    }

    @Test
    void testEditPriority() {
        int taskNumber = 1;
        final int taskIndex = 3;
        String newPriority = "medium";
        String expectedTaskName = "write report";
        tasks.setPriority(taskNumber, newPriority);
        Task taskEdited = tasks.getAll().get(taskIndex);
        String actualPriority = taskEdited.getPriorityObject().getPriority();
        String actualTaskName = taskEdited.getName();
        assertEquals(expectedTaskName, actualTaskName);
        assertEquals(newPriority, actualPriority);
    }

    @Test
    void testDisplayCollaboratorWithNoCollaborator() {
        String actualDisplay = tasks.showCollaborators(2);
        String expectedDisplay = "";
        assertEquals(expectedDisplay, actualDisplay);
    }

    @Test
    void testDisplayOneCollaborator() {
        String actualDisplay = tasks.showCollaborators(1);
        String expectedDisplay = "(1) aaron.\n";
        assertEquals(expectedDisplay, actualDisplay);
    }

    @Test
    void testDisplayMoreThanOneCollaborator() {
        tasks.setAttribute(1, "david");
        String actualDisplay = tasks.showCollaborators(1);
        String expectedDisplay = "(1) aaron.\n(2) david.\n";
        assertEquals(expectedDisplay, actualDisplay);
    }

    @Test
    void testGetCollaboratorWithoutACollaborator() {
        int actualSize = tasks.getCollaboratorsSize(2);
        int expectedSize = 0;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testGetOneCollaborator() {
        int actualSize = tasks.getCollaboratorsSize(1);
        int expectedSize = 1;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testGetTwoCollaborator() {
        tasks.setAttribute(1, "david");
        int actualSize = tasks.getCollaboratorsSize(1);
        int expectedSize = 2;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testAddWorkTaskCollaborator() {
        String newCollaborator = "david";
        int taskNumber = 1;
        tasks.setAttribute(taskNumber, newCollaborator);
        int expectedSize = 2;
        int actualSize = tasks.getCollaboratorsSize(taskNumber);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testAddWorkTaskCollaboratorWithEmptyString() {
        String newCollaborator = "";
        int taskNumber = 1;
        assertThrows(IllegalArgumentException.class, () -> tasks.setAttribute(taskNumber, newCollaborator));
        int expectedSize = 1;
        int actualSize = tasks.getCollaboratorsSize(taskNumber);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testAddPersonalTaskDetails() {
        String newDetails = "new hobby";
        int taskNumber = 2;
        tasks.setAttribute(taskNumber, newDetails);
        String actualDetails = ((PersonalTask) tasks.getPersonal().get(1)).getDetails();
        assertEquals(newDetails, actualDetails);
    }

    @Test
    void testAddPersonalTaskDetailsWithEmptyString() {
        String newDetails = "";
        int taskNumber = 2;
        assertThrows(IllegalArgumentException.class, () -> tasks.setAttribute(taskNumber, newDetails));
        String actualDetails = ((PersonalTask) tasks.getPersonal().get(1)).getDetails();
        assertEquals(newDetails, actualDetails);
    }

    @Test
    void testSetRemarks() {
        String newRemarks = "test remarks";
        int taskNumber = 1;
        tasks.setRemarks(taskNumber, newRemarks);
        int taskIndex = 3;
        String actualRemarks = tasks.getAll().get(taskIndex).getRemarks();
        assertEquals(newRemarks, actualRemarks);
    }

    @Test
    void readTasksToSystem() {
        tasks.writeToFile(username);
        tasks = new TaskCollection(motivationalQuotes, username);
        int expectedNoOfWorkTask = 2;
        int expectedNoOfPersonalTask = 2;
        int actualNoOfWorkTask = tasks.getWork().size();
        int actualNoOfPersonalTask = tasks.getPersonal().size();
        assertEquals(expectedNoOfPersonalTask, actualNoOfPersonalTask);
        assertEquals(expectedNoOfWorkTask, actualNoOfWorkTask);
    }

    @Test
    void testWriteTasksToSystem() {
        tasks.getAll().clear();
        TaskInfo taskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium", "");
        Task task = new WorkTask(taskInfo, motivationalQuotes);
        tasks.addTaskWithSummary(task);
        tasks.writeToFile(username);
        tasks = new TaskCollection(motivationalQuotes, username);
        int expectedNoOfTask = 1;
        int actualNoOfTask = tasks.getAll().size();
        assertEquals(expectedNoOfTask, actualNoOfTask);
    }

    @Test
    void testRemoveAllTasks() {
        assertEquals(true, tasks.clear());
        assertEquals(0, tasks.getAll().size());
        assertEquals(0, tasks.getWork().size());
        assertEquals(0, tasks.getPersonal().size());
    }

    @Test
    void testRemoveEmptyTasksCollection() {
        tasks.getAll().clear();
        assertEquals(false, tasks.clear());
    }

    @Test
    void testRemoveSpecificTask() {
        String taskNameToRemove = "write report";
        int taskNumberToRemove = 1;
        tasks.remove(taskNumberToRemove);

        List<String> taskList = tasks.getAll().stream()
                .map(task -> task.getName())
                .collect(Collectors.toList());
        boolean actualResult = taskList.contains(taskNameToRemove);
        assertEquals(3, tasks.getAll().size());
        assertEquals(false, actualResult);
        assertEquals(2, tasks.getPersonal().size());
        assertEquals(1, tasks.getWork().size());
    }

    @Test
    void testGetSummary() {
        List<String> expectedSummary = new ArrayList<>();
        expectedSummary.add(String.format("%-48s%-2s%d\n", "Total number of task", ":", tasks.getAll().size()));
        expectedSummary.add(String.format("%-48s%-2s%d", "Total number of personal task", ":", tasks.getPersonal().size()));
        expectedSummary.add(String.format("%-48s%-2s%d", "Total number of personal task that are overdue", ":", 1));
        expectedSummary.add(String.format("%-48s%-2s%d\n", "Total number of personal task that are done", ":", 0));
        expectedSummary.add(String.format("%-48s%-2s%d", "Total number of work of task", ":", 2));
        expectedSummary.add(String.format("%-48s%-2s%d", "Total number of work task that are overdue", ":", 0));
        expectedSummary.add(String.format("%-48s%-2s%d\n", "Total number of work task that are done", ":", 1));
        assertEquals(expectedSummary, tasks.getSummary());
    }

    @Test
    void testGetSummaryWithNoTask() {
        tasks.clear();
        List<String> expectedSummary = new ArrayList<>();
        expectedSummary.add(String.format("%-48s%-2s%d\n", "Total number of task", ":", 0));
        expectedSummary.add(String.format("%-48s%-2s%d", "Total number of personal task", ":", 0));
        expectedSummary.add(String.format("%-48s%-2s%d", "Total number of personal task that are overdue", ":", 0));
        expectedSummary.add(String.format("%-48s%-2s%d\n", "Total number of personal task that are done", ":", 0));
        expectedSummary.add(String.format("%-48s%-2s%d", "Total number of work of task", ":", 0));
        expectedSummary.add(String.format("%-48s%-2s%d", "Total number of work task that are overdue", ":", 0));
        expectedSummary.add(String.format("%-48s%-2s%d\n", "Total number of work task that are done", ":", 0));
        assertEquals(expectedSummary, tasks.getSummary());
    }
}
