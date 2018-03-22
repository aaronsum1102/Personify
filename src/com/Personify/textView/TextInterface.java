package com.Personify.textView;

import java.io.IOException;
import java.util.*;

import com.Personify.base.Priority;
import com.Personify.base.Status;
import com.Personify.controller.Controller;
import com.Personify.integration.Messenger;
import com.Personify.integration.TaskInfo;

/**
 * Interface for interacting with user.
 * 
 * @author aaronsum
 * @version 2.0, 2018-03-13
 */
public class TextInterface {
	private Controller controller;
	private Messenger messenger;
	private Command command;
	private Deque<Integer> menuStack;
	private String INVALID_COMMAND_MESSAGE = "Warning: Unfortunately, I can't understand you. Please try again";
	private final int INDEX_OF_MAIN_MENU = 1;
	private final int INDEX_OF_SHOW_TASKS_MENU = 2;
	private final int INDEX_OF_EDIT_MENU = 3;

	/**
	 * Instantiate an object for the interacting with user. At the same time it will
	 * instantiate a {@link Controller} object and {@link Messenger} object.
	 * 
	 * @throws IOException
	 *             If an input or output error occurred during the operation.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public TextInterface() throws IOException {
		controller = new Controller();
		messenger = controller.getMessages();
		command = new Command(messenger);
		menuStack = new ArrayDeque<>();
	}

	private void showMessagesWithHeaders(final List<String> messages) {
		if (!messages.isEmpty()) {
			System.out.println("----------------------------------Personify----------------------------------");
			messages.stream().forEach(System.out::println);
			System.out.println("-----------------------------------------------------------------------------");
			controller.clearMessages();
		}
	}

	private void toProceed(final Scanner commandReader) {
		System.out.println("press \"ENTER\" to continue...");
		commandReader.nextLine();
	}

	/**
	 * Responsible for running of the program until user choose to exit.
	 * 
	 * @throws IOException
	 *             If an input or output error occurred in
	 *             <code>showWelcomeMessage()</code>.
	 * @throws NumberFormatException
	 *             If user gave an input other than number when requested.
	 */
	public void startup() throws IOException{
		showWelcomeMessage();
		operationLoop();
	}

	private void showWelcomeMessage() throws IOException {
		messenger.addMessage("Welcome!");
		controller.readTaskDataToSystem();
		int numberOfTaskInRecord = controller.getTasks().getTasks().size();
		messenger.addMessage(String.format("You have %d task in my record.\n", numberOfTaskInRecord));
	}

	private void operationLoop() throws IOException {
		boolean isEnding = false;
		Scanner commandReader = new Scanner(System.in);
		while (!isEnding) {
			if (menuStack.isEmpty()) menuStack.add(INDEX_OF_MAIN_MENU);
			int index = menuStack.pop();
			switch (index) {
				case 1:
					isEnding = mainMenuOperation(isEnding, commandReader);
					continue;
				case 2:
					showTaskOperation(isEnding, commandReader);
					continue;
				case 3:
					editTaskOperation(isEnding, commandReader);
					continue;
			}
		}
	}

	private void invalidCommandWarning(final int CommandToExit, final int userSelection) {
		if (!(0 < userSelection && userSelection <= CommandToExit)) {
			System.out.println(INVALID_COMMAND_MESSAGE);
		}
	}

	private void showCommandMessage() {
		command.getMainCommands();
		showMessagesWithHeaders(messenger.getMessages());
	}

	private TaskInfo getTaskInfoFromUser(final Scanner taskInfoReader) {
		System.out.println("Please give a name for your task.");
		String taskName = taskInfoReader.nextLine();
		System.out.println("Please give a due date in format of YYYY-MM-DD.");
		String taskDueDate = taskInfoReader.nextLine();
		System.out.format("Plase give a status for your task . Valid status is %s.\n", Status.getStatuses());
		String taskStatus = taskInfoReader.nextLine();
		System.out.format("Please give a priority for your task. Valid priority is %s.\n", Priority.getPriorities());
		String taskPriority = taskInfoReader.nextLine();
		TaskInfo newTaskInfo = new TaskInfo(taskName, taskDueDate, taskStatus, taskPriority);
		return newTaskInfo;
	}

	private boolean mainMenuOperation(boolean isEnding, Scanner commandReader) throws IOException {
		final int NO_OF_ELEMENT_TO_EXCLUDE = 2;
		final int COMMAND_TO_EXIT = command.getMainCommandsSize() - NO_OF_ELEMENT_TO_EXCLUDE;
		boolean isToEndProgram = false;
		
		while (!isEnding) {
			try {
				showCommandMessage();
				int inputFromUser = Integer.parseInt(commandReader.nextLine());
				invalidCommandWarning(COMMAND_TO_EXIT, inputFromUser);
				switch (inputFromUser) {
					case 1:
						menuStack.add(INDEX_OF_SHOW_TASKS_MENU);
						isEnding = true;
						break;
					case 2:
						controller.addTask(getTaskInfoFromUser(commandReader));
						showMessagesWithHeaders(controller.getSystemMessages());
						toProceed(commandReader);
						continue;
					case 3:
						menuStack.add(INDEX_OF_EDIT_MENU);
						isEnding = true;
						break;
					case 4:
						controller.removeAllTasks();
						showMessagesWithHeaders(controller.getSystemMessages());
						toProceed(commandReader);
						continue;
					case 5:
						isEnding = true;
						isToEndProgram = true;
						controller.writeTaskDataToSystem();
						System.out.println("Good bye!");
						break;
				}
			} catch (NumberFormatException e) {
				System.out.println(INVALID_COMMAND_MESSAGE);
			}
		}
		return isToEndProgram;
	}

	private void showTasksSelectionCommand() {
		command.getShowTasksCommands();
		showMessagesWithHeaders(messenger.getMessages());
	}

	private void showTaskOperation(boolean isEnding, Scanner commandReader) {
		final int NO_OF_ELEMENT_TO_EXCLUDE = 2;
		final int COMMAND_TO_EXIT = command.getShowTasksCommandsSize() - NO_OF_ELEMENT_TO_EXCLUDE;
		
		while (!isEnding) {
			try {
				showTasksSelectionCommand();
				int inputFromUser = Integer.parseInt(commandReader.nextLine());
				invalidCommandWarning(COMMAND_TO_EXIT, inputFromUser);
				switch (inputFromUser) {
					case 1:
						showMessagesWithHeaders(controller.getAllTasks());
						toProceed(commandReader);
						continue;
					case 2:
						showMessagesWithHeaders(controller.getTasksToComplete());
						toProceed(commandReader);
						continue;
					case 3:
						showMessagesWithHeaders(controller.getTasksWithSpecificStatus("done"));
						toProceed(commandReader);
						continue;
					case 4:
						showMessagesWithHeaders(controller.getTasksWithSpecificStatus("overdue"));
						toProceed(commandReader);
						continue;
					case 5:
						isEnding = true;
						break;
					}
			} catch (NumberFormatException e) {
				System.out.println(INVALID_COMMAND_MESSAGE);
			}
		}
	}

	private void showSubCommandMessageForEditingTask() {
		command.getEditCommands();
		showMessagesWithHeaders(messenger.getMessages());
	}

	private int getTaskNumberToEditFromUser(final Scanner reader) {
		messenger.addMessage("You have the following tasks in my record:");
		showMessagesWithHeaders(controller.getAllTasks());
		System.out.println("Please give me the number of the task that you would like to edit.");
		return Integer.parseInt(reader.nextLine());
	}

	private void editTaskOperation(boolean isEnding, Scanner commandReader) {
		final int NO_OF_ELEMENT_TO_EXCLUDE = 2;
		final int COMMAND_TO_EXIT_CURRENT_MENU = command.getEditCommandsSize() - NO_OF_ELEMENT_TO_EXCLUDE;
		int taskIndex = 0;

		while (!isEnding) {
			try {
				showSubCommandMessageForEditingTask();
				int inputFromUser = Integer.parseInt(commandReader.nextLine());
				invalidCommandWarning(COMMAND_TO_EXIT_CURRENT_MENU, inputFromUser);
				switch (inputFromUser) {
					case 1: case 2: case 3: case 4:
						taskIndex = getTaskNumberToEditFromUser(commandReader);
						switch (inputFromUser) {
						case 1:
							System.out.println("Please give me a new name for the task.");
							String newName = commandReader.nextLine();
							if (controller.isTaskNameValidForEdit(newName)) {
								controller.editTaskName(taskIndex, newName);
							}
							showMessagesWithHeaders(controller.getSystemMessages());
							toProceed(commandReader);
							continue;
						case 2:
							System.out.println("Please give me a new due date with the format of \"YYYY-MM-DD\".");
							String newDueDate = commandReader.nextLine();
							controller.editDueDate(taskIndex, newDueDate);
							showMessagesWithHeaders(controller.getSystemMessages());
							toProceed(commandReader);
							continue;
						case 3:
							System.out.format("Please give me a new status. Valid status is %s.\n", Status.getStatuses());
							String newStatus = commandReader.nextLine();
							controller.editTaskStatus(taskIndex, newStatus);
							showMessagesWithHeaders(controller.getSystemMessages());
							toProceed(commandReader);
							continue;
						case 4:
							System.out.format("Please give a new priority. Valid priority is %s.\n", Priority.getPriorities());
							String newPriority = commandReader.nextLine();
							controller.editTaskPriority(taskIndex, newPriority);
							showMessagesWithHeaders(controller.getSystemMessages());
							toProceed(commandReader);
							continue;
						}
					case 5:
						isEnding = true;
						break;
				}
			} catch (NumberFormatException e) {
				System.out.println(INVALID_COMMAND_MESSAGE);
			}
		}
	}
}
