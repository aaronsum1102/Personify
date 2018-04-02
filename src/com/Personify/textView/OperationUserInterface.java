package com.Personify.textView;

import com.Personify.base.Priority;
import com.Personify.base.Status;
import com.Personify.base.Task;
import com.Personify.controller.Controller;
import com.Personify.exception.IllegalUserInfoException;
import com.Personify.exception.InvalidCommandException;
import com.Personify.integration.TaskInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UserInterface for interacting with user.
 */
public class OperationUserInterface extends UserInterface {
    private final Controller controller;
    private final Deque<String> menuNameStack;
    private final Deque<Method> menuMethodStack;
    private final Switcher switcher;
    private final Method[] methods;
    private String currentUserProfileInUse;

    /**
     * Construct <code>OperationUserInterface</code> object for the interacting with user. At the same time it will call
     * {@link Controller#afterLogIn(String)} method to load data into system from file and construct an
     * <code>Deque</code> object to keep track of current menu type.
     *
     * @param controller              <code>Controller</code> object which was construct when system start up.
     * @param currentUserProfileInUse username of the user in current session.
     */
    public OperationUserInterface(final Controller controller, final String currentUserProfileInUse) {
        super();
        this.controller = controller;
        this.currentUserProfileInUse = currentUserProfileInUse;
        controller.afterLogIn(currentUserProfileInUse);
        menuNameStack = new ArrayDeque<>();
        menuMethodStack = new ArrayDeque<>();
        switcher = new Switcher();
        methods = switcher.getClass().getDeclaredMethods();
    }

    /**
     * Responsible for running of the program until user choose to exit.
     */
    public void operation() {
        showWelcomeMessage();
        operationLoop();
    }

    private void showWelcomeMessage() {
        messages.add("Welcome!\n");
        messages.add("Here is a summary of the tasks you have.");
        messages.addAll(controller.getTasksSummary());
    }

    private void operationLoop() {
        int commandToExit;
        int inputFromUser;
        while (true) {
            if (menuNameStack.isEmpty() && menuMethodStack.isEmpty()) {
                menuNameStack.add("main");
                menuMethodStack.add(methods[0]);
            }
            String currentMenuName = menuNameStack.pop();
            Method currentMethod = menuMethodStack.pop();
            try {
                commandToExit = getCommandNoToExitAndDisplayMenu(currentMenuName);
                inputFromUser = getUserInputSelectionAndSanityCheck(commandToExit);
                currentMethod.invoke(switcher, inputFromUser);
                showMessagesWithHeaders();
                if (inputFromUser != commandToExit && !currentMenuName.equals("main")) {
                    menuMethodStack.add(currentMethod);
                    menuNameStack.add(currentMenuName);
                }
            } catch (NumberFormatException e) {
                System.err.println("Warning: I'm smart enough to know you didn't given me a number. " +
                        "Don't try to challenge me.");
                menuMethodStack.add(currentMethod);
                menuNameStack.add(currentMenuName);
            } catch (InvalidCommandException | IllegalArgumentException e) {
                System.err.println(e.getMessage());
                menuMethodStack.add(currentMethod);
                menuNameStack.add(currentMenuName);
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.err.println("Danger: Please contact the system administrator immediately.");
            } finally {
                toProceed();
            }
        }
    }

    private TaskInfo getInputFromUser() {
        System.out.println("Please give a name for your task.");
        String taskName = commandReader.nextLine();
        controller.isTaskNameValid(taskName);
        System.out.println("Please give a due date in format of YYYY-MM-DD.");
        System.out.println("Tips: Default due date is today's date, " + LocalDate.now().toString() + ".");
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

    private int getTaskNumberToEditFromUser(final List<Task> tasks) {
        messages.add("You have the following tasks:");
        getTasksIntoFormatForDisplay(tasks);
        messages.add("");
        messages.add("Please give me the number of the task that you would like to edit.");
        showMessagesWithHeaders();
        return Integer.parseInt(commandReader.nextLine());
    }

    private void removeAllTask() {
        if (controller.removeAllTasks()) {
            messages.add("All tasks were removed.");
        } else {
            messages.add("You have no task to clear.");
        }
    }

    private void removeSpecificCollaborator() throws InvalidCommandException {
        int workTaskIndex = getTaskNumberToEditFromUser(controller.getAllWorkTasks());
        isValidCommand(controller.getAllWorkTasks().size(), workTaskIndex);
        int collaboratorNumberToEdit = getCollaboratorNumberToEditFromUser(workTaskIndex);
        isValidCommand(controller.getCollaboratorsSize(workTaskIndex), workTaskIndex);
        controller.deleteCollaborator(workTaskIndex, collaboratorNumberToEdit);
        messages.add("Successfully change collaborator name or details of the task.");
    }

    private int getCollaboratorNumberToEditFromUser(final int index) {
        messages.add(controller.getCollaboratorsForDisplay(index));
        messages.add("\nPlease give me the number of the task that you would like to edit.");
        showMessagesWithHeaders();
        return Integer.parseInt(commandReader.nextLine());
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
        if (controller.getMotivationalQuotesSize() == 0) {
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

    private void removeSpecificMotivationalQuote() {
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

    private void exitProgram() {
        controller.writeTaskDataToSystem(currentUserProfileInUse);
        controller.saveUserProfile();
        controller.saveUserMotivationalQuotes(currentUserProfileInUse);
        commandReader.close();
        System.out.println("Good bye!");
        System.exit(0);
    }

    class Switcher {
        void switchMainMenuOptions(final int inputFromUser) {
            switch (inputFromUser) {
                case 1:
                    menuMethodStack.add(methods[1]);
                    menuNameStack.add("show task");
                    break;
                case 2:
                    menuMethodStack.add(methods[2]);
                    menuNameStack.add("add task");
                    break;
                case 3:
                    menuMethodStack.add(methods[3]);
                    menuNameStack.add("edit task");
                    break;
                case 4:
                    menuMethodStack.add(methods[4]);
                    menuNameStack.add("delete task");
                    break;
                case 5:
                    menuMethodStack.add(methods[5]);
                    menuNameStack.add("personalise");
                    break;
                case 6:
                    exitProgram();
            }
        }

        void switchShowTasksOptions(final int inputFromUser) throws InvalidCommandException {
            String taskType;
            switch (inputFromUser) {
                case 1:
                    getTasksIntoFormatForDisplay(controller.getAllTasks());
                    break;
                case 2:
                    taskType = getTaskTypeOptions();
                    if (!taskType.isEmpty()) {
                        getTasksIntoFormatForDisplay(controller.getTasksToComplete(taskType));
                    }
                    break;
                case 3:
                    taskType = getTaskTypeOptions();
                    if (!taskType.isEmpty()) {
                        getTasksIntoFormatForDisplay(controller.getTasksWithSpecificStatus(taskType, "done"));
                    }
                    break;
                case 4:
                    taskType = getTaskTypeOptions();
                    if (!taskType.isEmpty()) {
                        getTasksIntoFormatForDisplay(controller.getTasksWithSpecificStatus(taskType, "overdue"));
                    }
                case 5:
                    break;
            }
        }

        void switchAddTaskOptions(final int inputFromUser) {
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
                case 3:
                    break;
            }
        }

        void switchEditTaskOptions(final int inputFromUser) throws InvalidCommandException {
            switch (inputFromUser) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                    int taskIndex = getTaskNumberToEditFromUser(controller.getAllTasks());
                    isValidCommand(controller.getAllTasks().size(), taskIndex);
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
                    removeSpecificCollaborator();
                    break;
                case 8:
                    break;
            }
        }

        void switchDeleteTaskOptions(final int inputFromUser) throws InvalidCommandException {
            switch (inputFromUser) {
                case 1:
                    int taskIndex = getTaskNumberToEditFromUser(controller.getAllTasks());
                    isValidCommand(controller.getAllTasks().size(), taskIndex);
                    controller.deleteSpecificTask(taskIndex);
                    messages.add("Task deleted.");
                    break;
                case 2:
                    removeAllTask();
                    break;
                case 3:
                    List<Task> filteredTasks = controller.getTasksWithSpecificStatus("Task", "done");
                    if (filteredTasks.isEmpty()) {
                        messages.add("You don't have any tasks to clear.");
                    } else {
                        controller.deleteTasksThatWereDone(filteredTasks);
                        messages.add(String.format("I have deleted %d task for you", filteredTasks.size()));
                    }
                case 4:
                    break;
            }
        }

        void switchPersonaliseOptions(final int inputFromUser) throws IllegalUserInfoException {
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
                    removeSpecificMotivationalQuote();
                    break;
                case 6:
                    controller.deleteAllQuotes();
                    System.out.println("All quotes were successfully deleted.");
                    break;
                case 7:
                    break;
            }
        }

        private String getTaskTypeOptions() throws InvalidCommandException {
            int commandToExit = getCommandNoToExitAndDisplayMenu("task type");
            int inputFromUser = getUserInputSelectionAndSanityCheck(commandToExit);
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
    }
}
