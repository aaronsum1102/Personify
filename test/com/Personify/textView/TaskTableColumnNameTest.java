package com.Personify.textView;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTableColumnNameTest {
    private TaskTableColumnName columnName;

    @BeforeEach
    void setUp() {
        columnName = new TaskTableColumnName();
    }

    @AfterEach
    void tearDown() {
        columnName = null;
    }

    @Test
    void testToString() {
        String column1 = "Task Name";
        String column2 = "Due Date";
        String column3 = "Status";
        String column4 = "Priority";
        String column5 = "Details/Collaborators";
        String column6 = "Remarks";
        String expectedOutput = String.format("%3s%-30s%-15s%-15s%-15s%-30s%-30s", " ",
                column1, column2, column3, column4, column5, column6);
        String actualOutput = columnName.toString();
        assertEquals(expectedOutput, actualOutput);
    }
}