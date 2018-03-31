package com.Personify.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusTest {
    LocalDate dueDate;
    Status status;
    String initialStatus;

    @BeforeEach
    void setup() {
        initialStatus = "in progress";
        dueDate = LocalDate.now();
        status = new Status(initialStatus, dueDate);
    }

    @AfterEach
    void tearDown() {
        status = null;
    }

    @Test
    void testConstructionOfStatusObject() {
        assertEquals(initialStatus, status.getStatus());
    }

    @Test
    void testConstructionOfStatusObjectWithDueDateBehindTodayDate() {
        LocalDate dueDate = LocalDate.now().minusDays(3);
        status = new Status(initialStatus, dueDate);
        assertEquals("overdue", status.getStatus());
    }

    @Test
    void testConstructionOfStatusObjectWithNoPriority() {
        status = new Status("", dueDate);
        assertEquals("to do", status.getStatus());
    }

    @Test
    void testConstructionOfStatusObjectWithInvalidPriority() {
        status = new Status("I don't know", dueDate);
        assertEquals("to do", status.getStatus());
    }

    @Test
    void testConstructionOfStatusObjectWithNumberAsPriority() {
        status = new Status("213", dueDate);
        assertEquals("to do", status.getStatus());
    }

    @Test
    void testSetStatusWithNewValidStatus() {
        String newStatus = "done";
        status.setStatus(newStatus);
        assertEquals(newStatus, status.getStatus());
    }

    @Test
    void testSetStatusWitInvalidStatus() {
        String newStatus = "To Dod";
        assertThrows(IllegalArgumentException.class, () -> status.setStatus(newStatus));
    }

    @Test
    void testSetStatusWithValidStatusInUpperCase() {
        String newStatus = "DONE";
        status.setStatus(newStatus);
        assertEquals(newStatus.toLowerCase(), status.getStatus());
    }

    @Test
    void testSetStatusWithValidStatusInMixedCase() {
        String newStatus = "DoNe";
        status.setStatus(newStatus);
        assertEquals(newStatus.toLowerCase(), status.getStatus());
    }

    @Test
    void testSetStatusWithRepeatedStatus() {
        String newStatus = "low";
        assertThrows(IllegalArgumentException.class, () -> status.setStatus(newStatus));
    }

    @Test
    void testSetStatusWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> status.setStatus(""));
    }

    @Test
    void testSetStatusWithNumberWrapInString() {
        assertThrows(IllegalArgumentException.class, () -> status.setStatus("12342"));
    }
}
