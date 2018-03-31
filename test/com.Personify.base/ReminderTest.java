package com.Personify.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReminderTest {
    private LocalDate now;

    @BeforeEach
    void setUp() {
        now = LocalDate.now();
    }

    @Test
    void TestFindDaysLeftWithOneDayToDueDate() {
        LocalDate dueDate = now.plusDays(1);
        Reminder reminder = new Reminder(dueDate);
        long dayLeft = reminder.findDaysLeft();
        assertEquals(1, dayLeft);
    }

    @Test
    void TestFindDaysLeftWithOneDayOverDue() {
        LocalDate dueDate = now.plusDays(-1);
        Reminder reminder = new Reminder(dueDate);
        long dayLeft = reminder.findDaysLeft();
        assertEquals(-1, dayLeft);
    }

    @Test
    void TestFindDaysLeftWithDueDateAsCurrentDate() {
        LocalDate dueDate = LocalDate.now();
        Reminder reminder = new Reminder(dueDate);
        long dayLeft = reminder.findDaysLeft();
        assertEquals(0, dayLeft);
    }

    @Test
    void testGetReminderWithOneDayLeft() {
        LocalDate dueDate = now.plusDays(1);
        Reminder r = new Reminder(dueDate);
        assertEquals("You have 1 day left to finish it. Hurry up", r.getReminder());
    }

    @Test
    void testGetReminderWithThreeDaysLeft() {
        LocalDate dueDate = now.plusDays(3);
        Reminder r = new Reminder(dueDate);
        assertEquals("You have 3 days left to due date. You are on track towards your target. Keep it up.", r.getReminder());
    }

    @Test
    void testGetReminderWithZeroDayLeft() {
        Reminder r = new Reminder(now);
        assertEquals("Today is the day. Are you ready?", r.getReminder());
    }

    @Test
    void testGetReminderWithOneDayOverdue() {
        LocalDate dueDate = now.minusDays(1);
        Reminder r = new Reminder(dueDate);
        assertEquals("You are 1 day behind target. Don't give up!", r.getReminder());
    }

    @Test
    void testGetReminderWithThreeDaysOverdue() {
        LocalDate dueDate = now.minusDays(3);
        Reminder r = new Reminder(dueDate);
        assertEquals("You task is overdue by 3 days. Do you want to revise your target date?", r.getReminder());
    }
}
