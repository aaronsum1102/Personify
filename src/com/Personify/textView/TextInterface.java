package com.Personify.textView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
	private String INVALID_COMMAND_MESSAGE = "Warning: Unfortunately, I can't understand you. Please try again";

	/**
	 * Instantiate an object for the interacting with user. At the same time it will
	 * instantiate a {@link Controller} object and {@link Messenger} object.
	 * 
	 * @throws IOException
	 *             If an input or output error occurred during the operation.
	 */
	public TextInterface() throws IOException {
		controller = new Controller();
		messenger = controller.getMessages();
		command = new Command();
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
	public void startup() throws IOException, NumberFormatException {
		showWelcomeMessage();
		mainOperationLoop();
	}

	private void showWelcomeMessage() throws IOException {
		messenger.addMessage("Welcome!");
		controller.readTaskDataToSystem();
		int numberOfTaskInRecord = controller.getTasks().getTasks().size();
		messenger.addMessage(String.format("You have %d task in my record.\n", numberOfTaskInRecord));
	}

	private void showCommandMessage() {
		showMessagesWithHeaders(command.getMainCommands());
	}

	private boolean isCommandSelectionValid(final int CommandToExit, final int userSelection) {
		if (0 < userSelection && userSelection <= CommandToExit) {
			return true;
		}
		return false;
	}

	private void mainOperationLoop() throws IOException, NumberFormatException {
		boolean isEnding = false;
		final int COMMAND_TO_EXIT = command.getMainCommands().size() - 2;
		while (!isEnding) {
			showCommandMessage();
			try {
				Scanner commandReader = new Scanner(System.in);
				int inputFromUser = Integer.parseInt(commandReader.nextLine());
				if (!(isCommandSelectionValid(COMMAND_TO_EXIT, inputFromUser))) {
					System.out.println(INVALID_COMMAND_MESSAGE);
					continue;
				}
				switch (inputFromUser) {
				case 1:
					showMessagesWithHeaders(controller.getAllTasksSortedByName());
					toProceed(commandReader);
					continue;
				case 2:
					controller.addTask(getTaskInfoFromUser(commandReader));
					showMessagesWithHeaders(controller.getSystemMessages());
					toProceed(commandReader);
					continue;
				case 3:
					editTaskOperationLoop(commandReader);
					continue;
				case 4:
					controller.removeAllTasks();
					showMessagesWithHeaders(controller.getSystemMessages());
					toProceed(commandReader);
					continue;
				case 5:
					isEnding = true;
					controller.writeTaskDataToSystem();
					System.out.println("Good bye!");
					commandReader.close();
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println(INVALID_COMMAND_MESSAGE);
			}
		}

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

	private void showSubCommandMessageForEditingTask() {
		showMessagesWithHeaders(command.getEditCommands());
	}

	private int getTaskNumberToEditFromUser(final Scanner reader) {
		messenger.addMessage("You have the following tasks in my record:");
		showMessagesWithHeaders(controller.getAllTasksSortedByName());
		System.out.println("Please give me the number of the task that you would like to edit.");
		return Integer.parseInt(reader.nextLine());
	}

	private void editTaskOperationLoop(final Scanner reader) throws IOException {
		boolean isEnging = false;
		final int COMMAND_TO_EXIT_CURRENT_MENU = command.getEditCommands().size() - 2;
		int index = 0;
		while (!isEnging) {
			try {
				showSubCommandMessageForEditingTask();
				int inputFromUser = Integer.parseInt(reader.nextLine());
				if (!isCommandSelectionValid(COMMAND_TO_EXIT_CURRENT_MENU, inputFromUser)) {
					System.out.println(INVALID_COMMAND_MESSAGE);
					continue;
				}
				if (inputFromUser != COMMAND_TO_EXIT_CURRENT_MENU) {
					index = getTaskNumberToEditFromUser(reader);
				}
				switch (inputFromUser) {
				case 1:
					System.out.println("Please give me a new name for the task.");
					String newName = reader.nextLine();
					if (controller.isTaskNameValidForEdit(newName)) {
						controller.editTaskName(index, newName);
					}
					showMessagesWithHeaders(controller.getSystemMessages());
					toProceed(reader);
					continue;
				case 2:
					System.out.println("Please give me a new due date with the format of \"YYYY-MM-DD\".");
					String newDueDate = reader.nextLine();
					controller.editDueDate(index, newDueDate);
					showMessagesWithHeaders(controller.getSystemMessages());
					toProceed(reader);
					continue;
				case 3:
					System.out.format("Please give me a new status. Valid status is %s.\n", Status.getStatuses());
					String newStatus = reader.nextLine();
					controller.editTaskStatus(index, newStatus);
					showMessagesWithHeaders(controller.getSystemMessages());
					toProceed(reader);
					continue;
				case 4:
					System.out.format("Please give a new priority. Valid priority is %s.\n", Priority.getPriorities());
					String newPriority = reader.nextLine();
					controller.editTaskPriority(index, newPriority);
					showMessagesWithHeaders(controller.getSystemMessages());
					toProceed(reader);
					continue;
				case 5:
					isEnging = true;
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println(INVALID_COMMAND_MESSAGE);
			} 
		}
	}
}
