package com.Personify.base;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Reminder is an object responsible for providing reminder. The class provide a
 * reminder message to user on the number of day left to due date.
 * 
 * @author aaronsum
 * @version 1.0, 2018-03-03
 *
 */
public class Reminder {
	private LocalDate dueDate;

	/**
	 * Constructs an reminder object with a due date.
	 * 
	 * @param dueDate
	 */
	public Reminder(final LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Find number of days between due date and current date.
	 * 
	 * @return Number of days to due date.
	 */
	public long findDaysLeft() {
		return LocalDate.now().until(dueDate, ChronoUnit.DAYS);
	}

	/**
	 * Provide a reminder message on the number days left to due date.
	 */
	public String getMessage() {
		long dayLeft = findDaysLeft();
		if (dayLeft > 1) {
			return "You have " + dayLeft + " days left to due date. You are on track towards your target. Keep it up.";
		} else if (dayLeft == 1) {
			return "You have 1 day left to finish it. Hurry up";
		} else if (dayLeft == 0) {
			return "Today is the day. Are you ready?";
		} else if (dayLeft == -1) {
			return "You are 1 day behind target. Don't give up!";
		} else {
			return "You task is overdue by " + Math.abs(dayLeft) + " days. Do you want to revise your target date?";
		}
	}
}
