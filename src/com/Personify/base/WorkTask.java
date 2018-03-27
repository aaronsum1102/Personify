package com.Personify.base;

import com.Personify.integration.TaskInfo;

import java.util.ArrayList;
import java.util.List;

public class WorkTask extends Task {
    private final List<String> collaborators;

    public WorkTask(final TaskInfo taskInfo, final Motivation motivationQuotes) {
        super(taskInfo, motivationQuotes);
        collaborators = new ArrayList<>();
    }

    public void addCollaborators(final String collaboratorName) {
        collaborators.add(collaboratorName);
    }

    public String getCollaborators() {
        String collaboratorsString = collaborators.toString();
        return collaboratorsString.substring(1, collaboratorsString.length() - 1);
    }

    @Override
    public String toString() {
        String collaboratorsName = getCollaborators();
        return String.format("%-75s%-30s%-30s", super.toString(), collaboratorsName, remarks);
    }

    public String getSummary() {
        String summary = super.getSummary();
        summary += (getCollaborators() + "\n");
        summary += super.getMotivationalQuote();
        return summary;
    }
}
