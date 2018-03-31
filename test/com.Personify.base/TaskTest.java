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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {
    Task task;
    TaskInfo taskInfo;
    Motivation motivationQuotes;
    String taskName;
    String dueDate;
    String status;
    String priority;
    String remarks;

    @BeforeEach
    void setUp() {
        motivationQuotes = new Motivation("abc");
        taskName = "Doing grocery";
        dueDate = "2018-03-10";
        status = "to do";
        priority = "high";
        remarks = "";
        taskInfo = new TaskInfo(taskName, dueDate, status, priority, remarks);
        task = new WorkTask(taskInfo, motivationQuotes);
    }

    @AfterEach
    void tearDown() throws IOException {
        task = null;
        Path path = Paths.get("src/data", "abc_motivationalQuotes.txt");
        Files.deleteIfExists(path.getFileName());
    }

    @Test
    void testNameIsNotEmptyString() {
        String name = "Doing shopping for Christmas";
        boolean result = Task.isNameNotEmptyString(name);
        assertEquals(true, result);
    }

    @Test
    void testNameIsEmptyString() {
        String name = "";
        boolean result = Task.isNameNotEmptyString(name);
        assertEquals(true, !result);
    }

    @Test
    void testChangeNameWithNewName() {
        String newName = "Doing shopping for Christmas";
        boolean result = task.changeName(newName);
        assertEquals(true, result);
        assertEquals(newName, task.getName());
    }

    @Test
    void testChangeNameWithRepeatedName() {
        String newName = taskName;
        boolean result = task.changeName(newName);
        assertEquals(false, result);
    }

    @Test
    void testChangeNameWithRepeatedNameButPartiallyUpperCase() {
        String newName = "DOING grocery";
        boolean result = task.changeName(newName);
        assertEquals(false, result);
        assertEquals(taskName, task.getName());
    }

    @Test
    void testChangeNameWithRepeatedNameButLowerCase() {
        String newName = "doing grocery";
        boolean result = task.changeName(newName);
        assertEquals(false, result);
        assertEquals(taskName, task.getName());
    }

    @Test
    void testInitialisationOfDueDate() {
        assertEquals(LocalDate.parse(dueDate), task.getDueDate());
    }

    @Test
    void testInitialisationOfDueDateWithoutGivenADate() {
        taskInfo = new TaskInfo(taskName, "", status, priority, remarks);
        task = new WorkTask(taskInfo, motivationQuotes);
        assertEquals(LocalDate.now(), task.getDueDate());
    }

    @Test
    void testInitialisationOfDueDateWithIncorrectFormat() {
        taskInfo = new TaskInfo(taskName, "03-02-2018", status, priority, remarks);
        task = new WorkTask(taskInfo, motivationQuotes);
        assertEquals(LocalDate.now(), task.getDueDate());
    }

    @Test
    void testChangeNameWithEmptyString() {
        String newName = "";
        boolean result = task.changeName(newName);
        assertEquals(false, result);
        assertEquals(taskName, task.getName());
    }

    @Test
    void testChangeDueDateWithValidDateFormat() {
        String newDueDate = "2018-04-04";
        task.changeDueDate(newDueDate);
        assertEquals(LocalDate.parse(newDueDate), task.getDueDate());
    }

    @Test
    void testChangeDueDateWithSameDate() {
        String newDueDate = dueDate.toString();
        assertThrows(IllegalArgumentException.class, () -> task.changeDueDate(newDueDate));
    }

    @Test
    void testChangeDueDateWithInvalidDateFormat() {
        String newDueDate = "2018-3-10";
        assertThrows(IllegalArgumentException.class, () -> task.changeDueDate(newDueDate));
    }

    @Test
    void testChangeDueDateWithoutADate() {
        String newDueDate = "";
        assertThrows(IllegalArgumentException.class, () -> task.changeDueDate(newDueDate));
    }

    @Test
    void testSetPriority() {
        String newPriority = "medium";
        task.setPriority(newPriority);
        assertEquals(newPriority, task.getPriorityObject().getPriority());
    }

    @Test
    void testSetStatus() {
        String newStatus = "done";
        task.setStatus(newStatus);
        assertEquals(newStatus, task.getStatusObject().getStatus());

    }
}
