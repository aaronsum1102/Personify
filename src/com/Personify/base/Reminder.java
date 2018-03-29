package com.Personify.base;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Responsible for providing reminder to the user. The class provide a reminder message base on
 * the number of day left to due date.
 */
public class Reminder {
    private final LocalDate dueDate;

    /**
     * Instantiate a reminder object with the specify due date.
     *
     * @param dueDate Due date of the object.
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
     * Provide a reminder message based on the number day/days left to due date.
     *
     * @return a reminder messages for a specific <code>Task</code> object
     */
    public String getReminder() {
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
