package com.Personify.base;

import com.Personify.integration.TaskInfo;

public class PersonalTask extends Task {
    private String details;

    public PersonalTask(final TaskInfo taskInfo, final Motivation motivationQuotes, final String details) {
        super(taskInfo, motivationQuotes);
        this.details = details;
    }

    public void setDetails (final String newDetails) {
        details = newDetails;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return String.format("%-75s%-20s%-20s", super.toString(), details, remarks);

    }
}
