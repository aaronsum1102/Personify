package com.Personify.base;

import com.Personify.integration.TaskInfo;

public class PersonalTask extends Task {
    private String details;

    public PersonalTask(final TaskInfo taskInfo, final Motivation motivationQuotes, final String details) {
        super(taskInfo, motivationQuotes);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(final String newDetails) {
        details = newDetails;
    }

    @Override
    public String toString() {
        return String.format("%-75s%-30s%-30s", super.toString(), details, remarks);

    }

    public String getSummary() {
        String summary = super.getSummary();
        summary += String.format("%-12s%-2s%s\n", "Details", ":", details);
        summary += String.format("%-12s%-2s%s\n", "Remarks", ":", remarks);
        summary += String.format("Reminder : %s\n", reminder.getReminder());
        String motivationalQuote = super.getMotivationalQuote();
        if (!motivationalQuote.isEmpty()) {
            summary += String.format("\nQuote of the day: \n\"%s\".", super.getMotivationalQuote());
        }
        return summary;
    }
}
