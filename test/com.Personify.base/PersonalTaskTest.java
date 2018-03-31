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

class PersonalTaskTest {
    PersonalTask task;
    TaskInfo taskInfo;
    Motivation motivationQuotes;
    String taskName;
    String dueDate;
    String status;
    String priority;
    String remarks;
    String details;

    @BeforeEach
    void setUp() {
        motivationQuotes = new Motivation("abc");
        taskName = "Doing grocery";
        dueDate = LocalDate.now().minusDays(2).toString();
        status = "to do";
        priority = "high";
        remarks = "";
        details = "buy lots of vegetables";
        taskInfo = new TaskInfo(taskName, dueDate, status, priority, remarks);
        task = new PersonalTask(taskInfo, motivationQuotes, details);
    }

    @AfterEach
    void tearDown() throws IOException {
        task = null;
        Path path = Paths.get("src/data", "abc_motivationalQuotes.txt");
        Files.deleteIfExists(path.getFileName());
    }

    @Test
    void testConstructPersonalTaskObject() {
        assertEquals(taskName, task.getName());
        assertEquals(dueDate, task.getDueDate().toString());
        assertEquals(details, task.getDetails());
    }

    @Test
    void testGetDetails() {
        assertEquals(details, task.getDetails());
    }

    @Test
    void testSetDetails() {
        String newDetails = "this is for unit testing";
        task.setDetails(newDetails);
        assertEquals(newDetails, task.getDetails());
    }

    @Test
    void testToString() {
        String toStringOutputFromSuperClass = String.format("Doing grocery                 %s     overdue        high", LocalDate.now().minusDays(2));
        String expectedResult = String.format("%-75s%-30s%-30s", toStringOutputFromSuperClass, task.getDetails(), task.getRemarks());
        assertEquals(expectedResult, task.toString());
    }

    @Test
    void testGetSummary() {
        String expectedResult = "Here is a summary of the task that you had just added.\n" +
                "Task name   : Doing grocery\n" +
                String.format("Due date    : %s\n", LocalDate.now().minusDays(2)) +
                "Status      : overdue\n" +
                "Priority    : high\n" +
                "Details     : buy lots of vegetables\n" +
                "Remarks     : \n" +
                "Reminder    : You task is overdue by 2 days. Do you want to revise your target date?\n";
        assertEquals(expectedResult, task.getSummary());
    }

}