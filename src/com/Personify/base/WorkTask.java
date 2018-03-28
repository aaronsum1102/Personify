package com.Personify.base;

import com.Personify.integration.TaskInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkTask extends Task {
    private final List<String> collaborators;

    public WorkTask(final TaskInfo taskInfo, final Motivation motivationQuotes) {
        super(taskInfo, motivationQuotes);
        collaborators = new ArrayList<>();
    }

    public void addCollaborators(final String collaboratorName) {
        collaborators.add(collaboratorName);
    }

    public String getCollaboratorsAsString() {
        String collaboratorsString = collaborators.toString();
        return collaboratorsString.substring(1, collaboratorsString.length() - 1);
    }

    public String getCollaboratorsForDisplay() {
        AtomicInteger number = new AtomicInteger(1);
        String collaborators = "";
        for (String collaborator: this.collaborators) {
            collaborators = collaborators.concat(String.format("(%d) %s.\n", number.getAndIncrement(), collaborator));
        }
        return collaborators;
    }

    public List<String> getCollaborators() {
        return collaborators;
    }

    @Override
    public String toString() {
        String collaboratorsName = getCollaboratorsAsString();
        return String.format("%-75s%-30s%-30s", super.toString(), collaboratorsName, remarks);
    }

    public String getSummary() {
        String summary = super.getSummary();
        summary += (getCollaboratorsAsString() + "\n");
        summary += super.getMotivationalQuote();
        return summary;
    }
}
