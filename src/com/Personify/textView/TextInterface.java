package com.Personify.textView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.Personify.base.Priority;
import com.Personify.base.Status;
import com.Personify.controller.Controller;
import com.Personify.integration.Messages;
import com.Personify.integration.TaskInfo;

public class TextInterface {
	private Controller controller;
	private Messages messages;

	public TextInterface() throws IOException {
		controller = new Controller();
		messages = controller.getMessages();
	}

	private void showMessagesWithHeaders(final List<String> messages) {
		if (messages.size() > 0) {
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

	public void startup() throws IOException, NumberFormatException {
		showWelcomeMessage();
		boolean isProgramEnd = false;
		while (!isProgramEnd)
			try {
				mainOperationLoop();
				isProgramEnd = true;
			} catch (NumberFormatException e) {
				System.out.println("Warning: Unfortunately, I can't understand you.");
			}
	}

	private void showWelcomeMessage() throws IOException {
		messages.addMessage("Welcome!");
		controller.readTaskDataToSystem();
		messages.addMessage(String.format("You have %d task in my record.\n", controller.getTasks().getTasks().size()));
	}

	private void showCommandMessage() {
		messages.addMessage("How can I serve you? Give me a number and I will show you the magic.");
		messages.addMessage("(1) Show the name of all your tasks.");
		messages.addMessage("(2) Add new task.");
		messages.addMessage("(3) Edit Task.");
		messages.addMessage("(4) Save and quit.");
		showMessagesWithHeaders(messages.getMessages());
	}

	private void mainOperationLoop() throws IOException, NumberFormatException {
		boolean isEnd = false;
		while (!isEnd) {
			showCommandMessage();
			Scanner commandReader = new Scanner(System.in);
			int inputFromUser = Integer.parseInt(commandReader.nextLine());
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
				isEnd = true;
				controller.writeTaskDataToSystem();
				System.out.println("Good bye!");
				break;
			}
			commandReader.close();
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
		messages.addMessage("Which item do you want to edit?");
		messages.addMessage("(1) Task name.");
		messages.addMessage("(2) Due Date.");
		messages.addMessage("(3) Status.");
		messages.addMessage("(4) Priority.");
		messages.addMessage("(5) Go back to previous menu.");
		showMessagesWithHeaders(messages.getMessages());
	}

	private String getTaskNameToEditFromUser(final Scanner reader) {
		System.out.println("Please give me the name of the task that you would like to edit.");
		return reader.nextLine();
	}

	private void editTaskOperationLoop(final Scanner reader) throws IOException, NumberFormatException {
		boolean isEnd = false;
		String originalName = "";
		final int COMMAND_TO_EXIT_CURRENT_MENU = 5;
		while (!isEnd) {
			showSubCommandMessageForEditingTask();
			int inputFromUser = Integer.parseInt(reader.nextLine());
			if (inputFromUser != COMMAND_TO_EXIT_CURRENT_MENU) {
				originalName = getTaskNameToEditFromUser(reader);
			}
			switch (inputFromUser) {
			case 1:
				if (controller.isTaskNameValidForEdit(originalName)) {
					System.out.println("Please give me a new name for the task.");
					String newName = reader.nextLine();
					if (controller.isTaskNameValid(newName)) {
						controller.editTaskName(originalName, newName);
					}
				}
				showMessagesWithHeaders(controller.getSystemMessages());
				toProceed(reader);
				continue;
			case 2:
				if (controller.isTaskNameValidForEdit(originalName)) {
					System.out.println("Please give me a new due date with the format of \"YYYY-MM-DD\".");
					String newDueDate = reader.nextLine();
					controller.editDueDate(originalName, newDueDate);
				}
				showMessagesWithHeaders(controller.getSystemMessages());
				toProceed(reader);
				continue;
			case 3:
				if (controller.isTaskNameValidForEdit(originalName)) {
					System.out.format("Please give me a new status. Valid status is %s.\n", Status.getStatuses());
					String newStatus = reader.nextLine();
					controller.editTaskStatus(originalName, newStatus);
				}
				showMessagesWithHeaders(controller.getSystemMessages());
				toProceed(reader);
				continue;
			case 4:
				if (controller.isTaskNameValidForEdit(originalName)) {
					System.out.format("Please give a new priority. Valid priority is %s.\n", Priority.getPriorities());
					String newPriority = reader.nextLine();
					controller.editTaskPriority(newPriority, newPriority);
				}
				showMessagesWithHeaders(controller.getSystemMessages());
				toProceed(reader);
				continue;
			case 5:
				isEnd = true;
				break;
			}
		}
	}
}
