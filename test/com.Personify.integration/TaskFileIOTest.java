package com.Personify.integration;

import com.Personify.base.Motivation;
import com.Personify.base.Task;
import com.Personify.base.TaskCollection;
import com.Personify.base.WorkTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileIOTest {
    private TaskFileIO taskFileIO;
    private String username = "thisIsForUnitTest";
    private Path originalPath = Paths.get("src/data", "thisIsForUnitTest_tasksData.csv");
    private Path copyPath = Paths.get("src/data", "thisIsForUnitTest_tasksData copy.csv");

    @BeforeEach
    void setUp() {
        taskFileIO = new TaskFileIO(username);
        Motivation motivationalQuotes = new Motivation(username);
        TaskInfo taskInfo = new TaskInfo("write report", LocalDate.now().plusDays(1).toString(), "", "", "");
        Task task = new WorkTask(taskInfo, motivationalQuotes);
        TaskCollection taskCollection = new TaskCollection(motivationalQuotes, username);
        taskCollection.addTaskWithSummary(task);
        taskFileIO.writeTasksToFile(taskCollection.getAll(), username);
    }

    @AfterEach
    void tearDown() throws IOException {
        taskFileIO = null;
        Files.deleteIfExists(originalPath);
        Files.deleteIfExists(copyPath);
    }

    @Test
    void testReadTasksDataFromDataFile() {
        List<SubTaskInfo> taskData = taskFileIO.readTasksDataFromDataFile();
        int expectedSize = 1;
        int actualSize = taskData.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testReadTasksDataWithoutDataFile() throws IOException {
        Files.copy(originalPath, copyPath);
        Files.deleteIfExists(originalPath);
        List<SubTaskInfo> taskData = taskFileIO.readTasksDataFromDataFile();
        int expectedSize = 0;
        int actualSize = taskData.size();
        Files.copy(copyPath, originalPath);
        Files.deleteIfExists(copyPath);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void writeTasksToFile() throws IOException {
        Files.copy(originalPath, copyPath);
        Motivation motivationalQuotes = new Motivation(username);
        TaskInfo taskInfo = new TaskInfo("write story", LocalDate.now().plusDays(1).toString(), "", "", "");
        Task task = new WorkTask(taskInfo, motivationalQuotes);
        TaskCollection taskCollection = new TaskCollection(motivationalQuotes, username);
        taskCollection.addTaskWithSummary(task);
        taskFileIO.writeTasksToFile(taskCollection.getAll(), username);
        List<SubTaskInfo> taskData = taskFileIO.readTasksDataFromDataFile();
        int actualSize = taskData.size();
        int expectedSize = 2;
        Files.deleteIfExists(originalPath);
        Files.copy(copyPath, originalPath);
        Files.deleteIfExists(copyPath);
        assertEquals(expectedSize, actualSize);
    }
}