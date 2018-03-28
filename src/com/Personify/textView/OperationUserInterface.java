package com.Personify.textView;

import com.Personify.base.IllegalUserInfoException;
import com.Personify.base.Priority;
import com.Personify.base.Status;
import com.Personify.base.Task;
import com.Personify.controller.Controller;
import com.Personify.integration.TaskInfo;
import com.Personify.integration.TaskTableColumnName;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UserInterface for interacting with user.
 *
 * @author aaronsum
 */
public class OperationUserInterface extends UserInterface {
    private final Controller controller;
    private final Deque<String> menuStack;
    private String currentUserProfileInUse;

    /**
     * Instantiate an object for the interacting with user. At the same time it will
     * instantiate a {@link Controller} object.
     *
     */
    public OperationUserInterface(final Controller controller, final String currentUserProfileInUse) {
        super();
        this.controller = controller;
        this.currentUserProfileInUse = currentUserProfileInUse;
        controller.afterLogIn(currentUserProfileInUse);
        menuStack = new ArrayDeque<>();
    }

    /**
     * Responsible for running of the program until user choose to exit.
     */
    public void operation() {
        showWelcomeMessage();
        operationLoop();
    }

    private void showWelcomeMessage() {
        messages.add("Welcome!");
        messages.add(String.format("You have %d task in my record.\n", controller.getTasksSize()));
    }

    private void operationLoop() {
        boolean isEnding = false;
        while (!isEnding) {
            if (menuStack.isEmpty()) menuStack.add("main");
            String menu = menuStack.pop();
            try {
                switch (menu) {
                    case "main":
                        isEnding = mainMenuOperation(menu);
                        continue;
                    case "add task":
                        addTaskOperation(menu);
                        continue;
                    case "show task":
                        showTaskOperation(menu);
                        continue;
                    case "edit task":
                        editTaskOperation(menu);
                        continue;
                    case "delete task":
                        deleteTaskOperation(menu);
                    case "personalise":
                        personaliseOperation(menu);
                }
            } catch (NumberFormatException e) {
                System.err.println("Warning: I'm smart enough to know you didn't given me a number. Don't try to challenge me.");
                menuStack.add(menu);
                toProceed();
            } catch (InvalidCommandException | IllegalArgumentException | IllegalUserInfoException e) {
                System.err.println(e.getMessage());
                menuStack.add(menu);
                toProceed();
            }
        }
    }


    private TaskInfo getInputFromUser() {
        System.out.println("Please give a name for your task.");
        String taskName = commandReader.nextLine();
        controller.isTaskNameValid(taskName);
        System.out.println("Please give a due date in format of YYYY-MM-DD.");
        System.out.println("Tips: Default due date is today's date");
        String taskDueDate = commandReader.nextLine();
        System.out.format("Please give a status for your task . Valid status is %s.\n", Status.getStatuses());
        System.out.println("Tips: Default status is \"to do\"");
        String taskStatus = commandReader.nextLine();
        System.out.format("Please give a priority for your task. Valid priority is %s.\n", Priority.getPriorities());
        System.out.println("Tips: Default priority is \"high\"");
        String taskPriority = commandReader.nextLine();
        System.out.println("Please give me any remarks you want to add for the task.");
        String remarks = commandReader.nextLine();
        return new TaskInfo(taskName, taskDueDate, taskStatus, taskPriority, remarks);
    }

    private void removeAllTask() {
        if (controller.removeAllTasks()) {
            messages.add("All tasks were removed.");
        } else {
            messages.add("You have no task to remove.");
        }
    }

    private void exitProgram() {
        controller.writeTaskDataToSystem(currentUserProfileInUse);
        controller.saveUserProfile();
        controller.saveUserMotivationalQuotes(currentUserProfileInUse);
        commandReader.close();
        System.out.println("Good bye!");
    }

    private boolean mainMenuOperation(final String menuName) throws InvalidCommandException {
        final int noOfElementToSubtractForFormatting = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementToSubtractForFormatting;
        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        switch (inputFromUser) {
            case 1:
                menuStack.add("show task");
                break;
            case 2:
                menuStack.add("add task");
                break;
            case 3:
                menuStack.add("edit task");
                break;
            case 4:
                menuStack.add("delete task");
                break;
            case 5:
                menuStack.add("personalise");
                break;
            case 6:
                exitProgram();
                return true;
        }
        return false;
    }

    private String addPersonalTask(final TaskInfo newTaskInfo) {
        System.out.println("Please give me the details of the task.");
        final String details = commandReader.nextLine();
        return controller.addPersonalTaskAndGetSummary(newTaskInfo, details);
    }

    private String addWorkTask(final TaskInfo newTaskInfo) {
        System.out.println("Please give me collaborator\'s name of this task.");
        final String collaboratorName = commandReader.nextLine();
        return controller.addWorkTaskAndGetSummary(newTaskInfo, collaboratorName);
    }

    private void addTaskOperation(final String menuName) throws InvalidCommandException {
        final int noOfElementsToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementsToExclude;
        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);

        switch (inputFromUser) {
            case 1:
            case 2:
                final TaskInfo newTaskInfo = getInputFromUser();
                switch (inputFromUser) {
                    case 1:
                        messages.add(addPersonalTask(newTaskInfo));
                        break;
                    case 2:
                        messages.add(addWorkTask(newTaskInfo));
                        break;
                }
                showMessagesWithHeaders();
                toProceed();
            case 3:
                break;
        }
        if (inputFromUser != commandToExit) menuStack.add(menuName);
    }

    private void getTasksIntoFormatForDisplay(List<Task> tasks) {
        if (tasks.isEmpty()) {
            messages.add("You don't have any task. Do you want to add a task?");
        } else {
            AtomicInteger taskIndex = new AtomicInteger(1);
            TaskTableColumnName columnName = new TaskTableColumnName();
            messages.add(columnName.toString());
            tasks.forEach(task -> messages.add(String.format("%-3d%s", taskIndex.getAndIncrement(), task.toString())));
        }
    }

    private String getTaskTypeOperation() throws InvalidCommandException {
        final int noOfElementsToExclude = 2;
        final int commandToExit = menu.getMenu("task type").size() - noOfElementsToExclude;
        messages.addAll(menu.getMenu("task type"));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        switch (inputFromUser) {
            case 1:
                return "PersonalTask";
            case 2:
                return "WorkTask";
            case 3:
                break;
        }
        return "";
    }

    private void showTaskOperation(final String menuName) throws InvalidCommandException {
        final int noOfElementsToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementsToExclude;

        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        String taskType;
        switch (inputFromUser) {
            case 1:
                getTasksIntoFormatForDisplay(controller.getAllTasks());
                break;
            case 2:
                taskType = getTaskTypeOperation();
                if (!taskType.isEmpty()) {
                    getTasksIntoFormatForDisplay(controller.getTasksToComplete(taskType));
                }
                break;
            case 3:
                taskType = getTaskTypeOperation();
                if (!taskType.isEmpty()) {
                    getTasksIntoFormatForDisplay(controller.getTasksWithSpecificStatus(taskType, "done"));
                }
                break;
            case 4:
                taskType = getTaskTypeOperation();
                if (!taskType.isEmpty()) {
                    getTasksIntoFormatForDisplay(controller.getTasksWithSpecificStatus(taskType, "overdue"));
                }
            case 5:
                break;
        }
        showMessagesWithHeaders();
        toProceed();
        if (inputFromUser != commandToExit) menuStack.add(menuName);
    }

    private int getTaskNumberToEditFromUser(final List<Task> tasks) {
        messages.add("You have the following tasks:");
        getTasksIntoFormatForDisplay(tasks);
        messages.add("");
        messages.add("Please give me the number of the task that you would like to edit.");
        showMessagesWithHeaders();
        return Integer.parseInt(commandReader.nextLine());
    }

    private void deleteSpecificCollaborator() throws InvalidCommandException {
        int workTaskIndex = getTaskNumberToEditFromUser(controller.getAllWorkTasks());
        isValidCommand(controller.getAllWorkTasks().size(), workTaskIndex);
        int collaboratorNumberToEdit = getCollaboratorNumberToEditFromUser(workTaskIndex);
        isValidCommand(controller.getCollaboratorsSize(workTaskIndex), workTaskIndex);
        controller.deleteCollaborator(workTaskIndex, collaboratorNumberToEdit);
        messages.add("Successfully change collaborator name or details of the task.");
    }

    private int getCollaboratorNumberToEditFromUser(final int index){
        messages.add(controller.getCollaboratorsForDisplay(index));
        messages.add("\nPlease give me the number of the task that you would like to edit.");
        showMessagesWithHeaders();
        return Integer.parseInt(commandReader.nextLine());
    }

    private void editTaskOperation(final String menuName) throws InvalidCommandException {
        final int noOfElementToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementToExclude;

        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        switch (inputFromUser) {
            case 1: case 2: case 3: case 4: case 5: case 7:
                int taskIndex = getTaskNumberToEditFromUser(controller.getAllTasks());
                isValidCommand(controller.getTasksSize(), taskIndex);
                switch (inputFromUser) {
                    case 1:
                        System.out.println("Please give me a new name for the task.");
                        String newName = commandReader.nextLine();
                        if (controller.isTaskNameValid(newName)) {
                            controller.editTaskName(taskIndex, newName);
                            messages.add(String.format("Successfully change task name to %s.", newName));
                        }
                        break;
                    case 2:
                        System.out.println("Please give me a new due date with the format of \"YYYY-MM-DD\".");
                        String newDueDate = commandReader.nextLine();
                        controller.editDueDate(taskIndex, newDueDate);
                        messages.add("Successfully change the due date.");
                        break;
                    case 3:
                        System.out.format("Please give me a new status. Valid status is %s.\n", Status.getStatuses());
                        String newStatus = commandReader.nextLine();
                        controller.editTaskStatus(taskIndex, newStatus);
                        messages.add("Successfully change the status of the task.");
                        break;
                    case 4:
                        System.out.format("Please give a new priority. Valid priority is %s.\n", Priority.getPriorities());
                        String newPriority = commandReader.nextLine();
                        controller.editTaskPriority(taskIndex, newPriority);
                        messages.add("Successfully change the priority of the task.");
                        break;
                    case 5:
                        System.out.println("Please give me the collaborator name or details for the task.");
                        String newInfo = commandReader.nextLine();
                        controller.setAttribute(taskIndex, newInfo);
                        messages.add("Successfully change collaborator name or details of the task.");
                        break;
                    case 7:
                        System.out.println("Please give me the remarks for the task.");
                        String remarks = commandReader.nextLine();
                        controller.setRemarks(taskIndex, remarks);
                        messages.add("Successfully change collaborator name or details of the task.");
                        break;
                }
                break;
            case 6:
                deleteSpecificCollaborator();
                break;
            case 8:
                break;

        }
        showMessagesWithHeaders();
        toProceed();
        if (inputFromUser != commandToExit) menuStack.add(menuName);
    }

    private void deleteTaskOperation(final String menuName) throws InvalidCommandException {
        final int noOfElementToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementToExclude;
        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        switch (inputFromUser) {
            case 1:
                int taskIndex = getTaskNumberToEditFromUser(controller.getAllTasks());
                isValidCommand(controller.getTasksSize(), taskIndex);
                controller.deleteSpecificTask(taskIndex);
                messages.add("Task deleted.");
                break;
            case 2:
                removeAllTask();
                break;
            case 3:
                List<Task> filteredTasks = controller.getTasksWithSpecificStatus("Task","done");
                if (filteredTasks.isEmpty()) {
                    messages.add("You don't have any tasks to remove.");
                } else {
                    controller.deleteTasksThatWereDone(filteredTasks);
                    messages.add(String.format("I have deleted %d task for you", filteredTasks.size()));
                }
            case 4:
                break;
        }
        showMessagesWithHeaders();
        toProceed();
        if (inputFromUser != commandToExit) menuStack.add(menuName);
    }

    private void setUserName() throws IllegalUserInfoException {
        System.out.print("New username: ");
        String newUserName = commandReader.nextLine();
        if (controller.validateNewUserName(newUserName)) {
            controller.editUserName(currentUserProfileInUse, newUserName);
            currentUserProfileInUse = newUserName;
        }
    }

    private void setPassword() throws IllegalUserInfoException {
        System.out.print("Existing password: ");
        String currentPassword = commandReader.nextLine();
        System.out.print("New username: ");
        String newPassword = commandReader.nextLine();
        controller.editPassword(currentUserProfileInUse, currentPassword, newPassword);

    }

    private void showAllMotivationalQuote() {
        if(controller.getMotivationalQuotesSize() == 0) {
            messages.add("You don't have any motivational quotes to show.");
        } else {
            messages.add("You have the following motivational quote:");
            messages.add(controller.showAllMotivationalQuote());
        }
        showMessagesWithHeaders();
    }

    private void addMotivationalQuote() {
        System.out.println("Please provide a new motivational quote you want to add.");
        String newQuote = commandReader.nextLine();
        controller.addMotivationalQuote(newQuote);
    }

    private void deleteSpecificQuote() {
        showAllMotivationalQuote();
        System.out.println("Please provide the index of the quote you want to delete.");
        try {
            int index = Integer.parseInt(commandReader.nextLine());
            isValidCommand(controller.getMotivationalQuotesSize(), index);
            controller.deleteSpecificQuote(index);
            System.out.println("The quote was successfully deleted.");
        } catch (InvalidCommandException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Warning: You didn't give me a int number.");
        }
    }

    private void personaliseOperation(final String menuName) throws InvalidCommandException, IllegalUserInfoException {
        final int noOfElementToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementToExclude;
        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        switch (inputFromUser) {
            case 1:
                setUserName();
                System.out.println("New user name successfully registered.");
                break;
            case 2:
                setPassword();
                System.out.println("New password successfully registered.");
                break;
            case 3:
                showAllMotivationalQuote();
                break;
            case 4:
                addMotivationalQuote();
                System.out.println("New quote successfully registered.");
                break;
            case 5:
                deleteSpecificQuote();
                break;
            case 6:
                controller.deleteAllQuotes();
                System.out.println("All quotes were successfully deleted.");
                break;
            case 7:
                break;
        }
        toProceed();
        if (inputFromUser != commandToExit) menuStack.add(menuName);
    }
}
