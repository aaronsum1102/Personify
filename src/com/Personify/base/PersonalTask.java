package com.Personify.base;

import com.Personify.integration.TaskInfo;

/**
 * Manage personal related task and inherit functionality from {@link Task} class with additional functions to
 * manage additional details for an object.
 */
public class PersonalTask extends Task {
    private String details;

    /**
     * Instantiate a personal task object with user input details of the task.
     *
     * @param taskInfo         {@link TaskInfo} object which contains detail information of task.
     * @param motivationQuotes {@link Motivation} object which hold a collections of motivational quotes.
     * @param details          additional information of a <code>PersonalTask</code> object.
     */
    public PersonalTask(final TaskInfo taskInfo, final Motivation motivationQuotes, final String details) {
        super(taskInfo, motivationQuotes);
        this.details = details;
    }

    /**
     * Provide details of a <code>PersonalTask</code> object.
     *
     * @return details of a <code>PersonalTask</code> object.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Set the details of a <code>PersonalTask</code> object.
     *
     * @param newDetails new details of a <code>PersonalTask</code> object.
     */
    public void setDetails(final String newDetails) {
        details = newDetails;
    }

    /**
     * Return a String representation of this object. The String representation consist of the String representation
     * from calling method {@link Task#toString()}, details and remarks of the object.
     *
     * @return a string with all information of a <code>PersonalTask</code> object.
     */
    @Override
    public String toString() {
        return String.format("%-75s%-30s%-30s", super.toString(), details, remarks);
    }

    /**
     * Provide a summary of all information of a <code>PersonalTask</code> object.
     *
     * @return a string with a summary of task information.
     */
    public String getSummary() {
        String summary = super.getSummary();
        summary += String.format("%-12s%-2s%s\n", "Details", ":", details);
        summary += String.format("%-12s%-2s%s\n", "Remarks", ":", remarks);
        summary += String.format("%-12s%-2s%s\n", "Reminder", ":", reminder.getReminder());
        String motivationalQuote = super.getMotivationalQuote();
        if (!motivationalQuote.isEmpty()) {
            summary += String.format("\nQuote of the day: \n\"%s\".", super.getMotivationalQuote());
        }
        return summary;
    }
}
