package com.Personify.base;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReminderTest {
	LocalDate now;

	@BeforeEach
	void setUp() throws Exception {
		now = LocalDate.now();
	}

	@Test
	void testGetMessageWithOneDayLeft() {
		LocalDate dueDate = now.plusDays(1);
		Reminder r = new Reminder(dueDate);
		assertEquals("You have 1 day left to finish it. Hurry up", r.getMessage());
	}
	
	@Test
	void testGetMessageWithThreeDaysLeft() {
		LocalDate dueDate = now.plusDays(3);
		Reminder r = new Reminder(dueDate);
		assertEquals("You have 3 days left to due date. You are on track towards your target. Keep it up.", r.getMessage());	
	}
	
	@Test
	void testGetMessageWithZeroDayLeft() {
		Reminder r = new Reminder(now);
		assertEquals("Today is the day. Are you ready?", r.getMessage());
	}
	
	@Test
	void testGetMessageWithOneDayOverdue() {
		LocalDate dueDate = now.minusDays(1);
		Reminder r = new Reminder(dueDate);
		assertEquals("You are 1 day behind target. Don't give up!", r.getMessage());
	}
	
	@Test
	void testGetMessageWithThreeDaysOverdue() {
		LocalDate dueDate = now.minusDays(3);
		Reminder r = new Reminder(dueDate);
		assertEquals("You task is overdue by 3 days. Do you want to revise your target date?", r.getMessage());
	}
}
