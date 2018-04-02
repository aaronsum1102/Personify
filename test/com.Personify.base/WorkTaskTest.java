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

class WorkTaskTest {
    private WorkTask task;
    private TaskInfo taskInfo;
    private Motivation motivationQuotes;
    private String taskName;
    private String dueDate;
    private String status;
    private String priority;
    private String remarks;

    @BeforeEach
    void setUp() {
        motivationQuotes = new Motivation("abc");
        taskName = "Doing grocery";
        dueDate = LocalDate.now().minusDays(2).toString();
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
    void testAddCollaborators() {
        String collaborator = "aaron";
        task.addCollaborators(collaborator);
        assertEquals(true, task.getCollaborators().contains(collaborator));
    }

    @Test
    void testGetCollaboratorsAsString() {
        String collaborator = "aaron";
        String anotherCollaborator = "david";
        task.addCollaborators(collaborator);
        task.addCollaborators(anotherCollaborator);
        String expectedResult = collaborator + ", " + anotherCollaborator;
        assertEquals(expectedResult, task.getCollaboratorsAsString());
    }

    @Test
    void testGetCollaboratorsForDisplay() {
        String collaborator = "aaron";
        String anotherCollaborator = "david";
        task.addCollaborators(collaborator);
        task.addCollaborators(anotherCollaborator);
        String expectedResult = String.format("(%d) %s.\n(%d) %s.\n", 1, collaborator, 2, anotherCollaborator);
        assertEquals(expectedResult, task.getCollaboratorsForDisplay());
    }

    @Test
    void testGetCollaboratorsAsACollection() {
        String collaborator = "aaron";
        task.addCollaborators(collaborator);
        assertEquals(1, task.getCollaborators().size());
    }

    @Test
    void testToString() {
        String toStringOutputFromSuperClass = String.format("Doing grocery                 %s     overdue        high"
                , LocalDate.now().minusDays(2).toString());
        String expectedResult = String.format("%-75s%-30s%-30s", toStringOutputFromSuperClass, "", remarks);
        assertEquals(expectedResult, task.toString());
    }

    @Test
    void testGetSummary() {
        String expectedResult = "Here is a summary of the task that you had just added.\n" +
                "Task name   : Doing grocery\n" +
                String.format("Due date    : %s\n", LocalDate.now().minusDays(2).toString()) +
                "Status      : overdue\n" +
                "Priority    : high\n" +
                "Remarks     : \n" +
                "Collaborator: \n" +
                "Reminder    : You task is overdue by 2 days. Do you want to revise your target date?\n";
        assertEquals(expectedResult, task.getSummary());
    }
}