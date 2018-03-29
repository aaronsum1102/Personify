package com.Personify.base;

import com.Personify.integration.TaskInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manage work related task and inherit functionality from {@link Task} class with additional functions to manage a
 * collection of collaborator for a <code>WorkTask</code> object.
 */
public class WorkTask extends Task {
    private final List<String> collaborators;

    /**
     * Instantiate a work task object.
     *
     * @param taskInfo         {@link TaskInfo} object which contains detail information of task.
     * @param motivationQuotes {@link Motivation} object which hold a collections of motivational quotes.
     */
    public WorkTask(final TaskInfo taskInfo, final Motivation motivationQuotes) {
        super(taskInfo, motivationQuotes);
        collaborators = new ArrayList<>();
    }

    /**
     * Add collaborator for an work task object.
     *
     * @param collaboratorName name of the collaborator to add into collection of the object.
     */
    public void addCollaborators(final String collaboratorName) {
        collaborators.add(collaboratorName);
    }

    /**
     * Provide all the collaborators name for an object.
     *
     * @return a string with all the name the collaborator of an object.
     */
    public String getCollaboratorsAsString() {
        String collaboratorsString = collaborators.toString();
        return collaboratorsString.substring(1, collaboratorsString.length() - 1);
    }

    /**
     * Provide a String representation of all collaborators. Each collaborator will have an integer index follow by
     * collaborator name in a single line.
     *
     * @return a string representation of all collaborators with specific format.
     */
    public String getCollaboratorsForDisplay() {
        AtomicInteger number = new AtomicInteger(1);
        String collaborators = "";
        for (String collaborator : this.collaborators) {
            collaborators = collaborators.concat(String.format("(%d) %s.\n", number.getAndIncrement(), collaborator));
        }
        return collaborators;
    }

    /**
     * Provide a collection of all collaborators.
     *
     * @return a collection of all the collaborators.
     */
    public List<String> getCollaborators() {
        return collaborators;
    }

    /**
     * Provide a String of representation of the object. It include string representation from calling
     * {@link Task#toString()} method and all collaborators name and remarks of this object.
     *
     * @return a string representation of this object.
     */
    @Override
    public String toString() {
        String collaboratorsName = getCollaboratorsAsString();
        return String.format("%-75s%-30s%-30s", super.toString(), collaboratorsName, remarks);
    }

    /**
     * Provide a summary of this object with motivational quote based on the number of days left to due date.
     *
     * @return Summary of information of this object.
     */
    public String getSummary() {
        String summary = super.getSummary();
        summary += String.format("%-12s%-2s%s\n", "Remarks", ":", remarks);
        summary += String.format("%-12s%-2s%s\n", "Collaborator", ":", getCollaboratorsAsString());
        summary += String.format("%-12s%-2s%s\n", "Reminder", ":", reminder.getReminder());
        String motivationalQuote = super.getMotivationalQuote();
        if (!motivationalQuote.isEmpty()) {
            summary += String.format("\nReminder : \n\"%s\".", super.getMotivationalQuote());
        }
        return summary;
    }
}
