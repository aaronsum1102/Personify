package com.Personify.textView;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.Personify.base.Priority;
import com.Personify.base.Status;
import com.Personify.base.Task;
import com.Personify.controller.Controller;
import com.Personify.integration.TaskInfo;

/**
 * UserInterface for interacting with user.
 *
 * @author aaronsum
 * @version 2.0, 2018-03-13§
 */
public class OperationUserInterface extends UserInterface {
    private Controller controller;
    private Deque<String> menuStack;

    /**
     * Instantiate an object for the interacting with user. At the same time it will
     * instantiate a {@link Controller} object.
     *
     * @throws IOException If an input or output error occurred during the operation.
     */
    public OperationUserInterface(final Controller controller, final String currentUserProfileInUse) throws IOException {
        super();
        this.controller = controller;
        controller.afterLogIn(currentUserProfileInUse);
        menuStack = new ArrayDeque<>();
    }

    /**
     * Responsible for running of the program until user choose to exit.
     *
     */
    public void operation() {
        showWelcomeMessage();
        operationLoop();
    }

    private void showWelcomeMessage() {
        controller.readTaskDataToSystem();
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
                        isEnding = mainMenuOperation();
                        continue;
                    case "add task":
                        addTaskOperation();
                        continue;
                    case "show task":
                        showTaskOperation();
                        continue;
                    case "edit task":
                        editTaskOperation();
                        continue;
                    case "delete task":
                        deleteTaskOperation();
                }
            } catch (NumberFormatException e) {
                System.out.println("Warning: I'm smart enough to know you didn't given me a number. Don't try to challenge me.");
                menuStack.add(menu);
                toProceed();
            } catch (InvalidCommandException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
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
        return new TaskInfo(taskName, taskDueDate, taskStatus, taskPriority);
    }

    private void removeAllTask() {
        if (controller.removeAllTasks()) {
            messages.add("All tasks were removed.");
        } else {
            messages.add("You have no task to remove.");
        }
    }

    private boolean mainMenuOperation() throws InvalidCommandException {
        final String menuName = "main";
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
                controller.writeTaskDataToSystem();
                System.out.println("Good bye!");
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

    private void addTaskOperation() throws InvalidCommandException {
        final String menuName = "add task";
        final int noOfElementsToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementsToExclude;
        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);

        switch (inputFromUser) {
            case 1: case 2:
                final TaskInfo newTaskInfo = getInputFromUser();
                switch(inputFromUser) {
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
            String column1 = "Task Name";
            String column2 = "Due Date";
            String column3 = "Status";
            String column4 = "Priority";
            String column5 = "Details/Collaborators";
            messages.add(String.format("%3s%-30s%-15s%-15s%-15s%-20s", " ", column1, column2, column3, column4, column5));
            tasks.forEach(task -> messages.add(String.format("%-3d%s", taskIndex.getAndIncrement(), task.toString())));
        }
    }

    private void showTaskOperation() throws InvalidCommandException {
        final String menuName = "show task";
        final int noOfElementsToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementsToExclude;

        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        switch (inputFromUser) {
            case 1:
                getTasksIntoFormatForDisplay(controller.getAllTasks());
                showMessagesWithHeaders();
                toProceed();
                break;
            case 2:
                getTasksIntoFormatForDisplay(controller.getTasksToComplete());
                showMessagesWithHeaders();
                toProceed();
                break;
            case 3:
                getTasksIntoFormatForDisplay(controller.getTasksWithSpecificStatus("done"));
                showMessagesWithHeaders();
                toProceed();
                break;
            case 4:
                getTasksIntoFormatForDisplay(controller.getTasksWithSpecificStatus("overdue"));
                showMessagesWithHeaders();
                toProceed();
            case 5:
                break;
        }
        if (inputFromUser != commandToExit) menuStack.add(menuName);
    }

    private int getTaskNumberToEditFromUser() {
        messages.add("You have the following tasks:");
        getTasksIntoFormatForDisplay(controller.getAllTasks());
        messages.add("");
        messages.add("Please give me the number of the task that you would like to edit.");
        showMessagesWithHeaders();
        return Integer.parseInt(commandReader.nextLine());
    }

    private void editTaskOperation() throws InvalidCommandException {
        final String menuName = "edit task";
        final int noOfElementToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementToExclude;

        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        switch (inputFromUser) {
            case 1:
            case 2:
            case 3:
            case 4:
                int taskIndex = getTaskNumberToEditFromUser();
                isValidCommand(controller.getTasksSize(), taskIndex);
                switch (inputFromUser) {
                    case 1:
                        System.out.println("Please give me a new name for the task.");
                        String newName = commandReader.nextLine();
                        if (controller.isTaskNameValid(newName)) {
                            controller.editTaskName(taskIndex, newName);
                            messages.add(String.format("Successfully change task name to %s.", newName));
                        }
                        showMessagesWithHeaders();
                        toProceed();
                        break;
                    case 2:
                        System.out.println("Please give me a new due date with the format of \"YYYY-MM-DD\".");
                        String newDueDate = commandReader.nextLine();
                        controller.editDueDate(taskIndex, newDueDate);
                        messages.add("Successfully change the due date.");
                        showMessagesWithHeaders();
                        toProceed();
                        break;
                    case 3:
                        System.out.format("Please give me a new status. Valid status is %s.\n", Status.getStatuses());
                        String newStatus = commandReader.nextLine();
                        controller.editTaskStatus(taskIndex, newStatus);
                        messages.add("Successfully change the status of the task.");
                        showMessagesWithHeaders();
                        toProceed();
                        break;
                    case 4:
                        System.out.format("Please give a new priority. Valid priority is %s.\n", Priority.getPriorities());
                        String newPriority = commandReader.nextLine();
                        controller.editTaskPriority(taskIndex, newPriority);
                        messages.add("Successfully change the priority of the task.");
                        showMessagesWithHeaders();
                        toProceed();
                        break;
                }
            case 5:
                break;
        }
        if (inputFromUser != commandToExit) menuStack.add(menuName);
    }

    private void deleteTaskOperation() throws InvalidCommandException {
        final String menuName = "delete task";
        final int noOfElementToExclude = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementToExclude;

        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        switch (inputFromUser) {
            case 1:
                int taskIndex = getTaskNumberToEditFromUser();
                isValidCommand(controller.getTasksSize(), taskIndex);
                controller.deleteSpecificTask(taskIndex);
                messages.add("Task deleted.");
                showMessagesWithHeaders();
                toProceed();
                break;
            case 2:
                removeAllTask();
                showMessagesWithHeaders();
                toProceed();
                break;
            case 3:
                List<Task> filteredTasks = controller.getTasksWithSpecificStatus("done");
                if (filteredTasks.isEmpty()) {
                    messages.add("You don't have any tasks to remove.");
                } else {
                    controller.deleteTasksThatWereDone(filteredTasks);
                    messages.add(String.format("I have deleted %d task for you", filteredTasks.size()));
                }
                showMessagesWithHeaders();
                toProceed();
            case 4:
                break;
        }
        if (inputFromUser != commandToExit) menuStack.add(menuName);
    }

    //TODO add collaborator
    //TODO add details for personal task.
    //TODO add task summary.
    //TODO Layout for show task
    //TODO add remarks.
}
