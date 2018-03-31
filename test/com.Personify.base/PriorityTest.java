package com.Personify.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriorityTest {
    Priority priority;
    String initialPriority;
    LocalDate dueDate;

    @BeforeEach
    void setUp() {
        initialPriority = "low";
        dueDate = LocalDate.now();
        priority = new Priority(initialPriority, dueDate);
    }

    @AfterEach
    void tearDown() {
        priority = null;
    }

    @Test
    void testConstructionOfPriorityObject() {
        assertEquals(initialPriority, priority.getPriority());
    }

    @Test
    void testConstructionOfPriorityObjectWithDueDateBehindTodayDate() {
        LocalDate dueDate = LocalDate.now().minusDays(3);
        priority = new Priority(initialPriority, dueDate);
        assertEquals("high", priority.getPriority());
    }

    @Test
    void testConstructionOfPriorityObjectWithNoPriority() {
        priority = new Priority("", dueDate);
        assertEquals("high", priority.getPriority());
    }

    @Test
    void testConstructionOfPriorityObjectWithInvalidPriority() {
        priority = new Priority("I don't know", dueDate);
        assertEquals("high", priority.getPriority());
    }

    @Test
    void testConstructionOfPriorityObjectWithNumberAsPriority() {
        priority = new Priority("213", dueDate);
        assertEquals("high", priority.getPriority());
    }

    @Test
    void testSetPriorityWithNewPriority() {
        String newPriority = "Medium";
        priority.setPriority(newPriority);
        assertEquals(newPriority.toLowerCase(), priority.getPriority());
    }

    @Test
    void testSetPriorityWithValueNotInValidPriority() {
        String newPriority = "Lowa";
        assertThrows(IllegalArgumentException.class, () -> priority.setPriority(newPriority));
    }

    @Test
    void testSetPriorityWithEmptyString() {
        String newPriority = "";
        assertThrows(IllegalArgumentException.class, () -> priority.setPriority(newPriority));
    }

    @Test
    void testSetPriorityWithNumberWrapInString() {
        String newPriority = "124";
        assertThrows(IllegalArgumentException.class, () -> priority.setPriority(newPriority));
    }

    @Test
    void testSetPriorityWithSamePriority() {
        String newPriority = initialPriority;
        assertThrows(IllegalArgumentException.class, () -> priority.setPriority(newPriority));
    }

    @Test
    void testSetPriorityWithNewPriorityInLowerCase() {
        String priorityToCheck = "medium";
        priority.setPriority(priorityToCheck);
        assertEquals(priorityToCheck, priority.getPriority());
    }

    @Test
    void testSetPriorityWithNewPriorityInUpperCase() {
        String priorityToCheck = "MEDIUM";
        priority.setPriority(priorityToCheck);
        assertEquals(priorityToCheck.toLowerCase(), priority.getPriority());
    }

    @Test
    void testSetPriorityWithNewPriorityInCamelCase() {
        String priorityToCheck = "mEdiUm";
        priority.setPriority(priorityToCheck);
        assertEquals(priorityToCheck.toLowerCase(), priority.getPriority());
    }

}
