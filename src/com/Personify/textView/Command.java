package com.Personify.textView;

import java.io.IOException;
import java.util.List;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;
import com.Personify.integration.Messenger;

public class Command {
	private Messenger messenger;
	private List<String> commands;
	private List<String> mainCommands;
	private List<String> editCommands;
	private List<String> showTasksCommands;
	
	public Command(Messenger messenger) throws IOException {
		this.messenger = messenger; 
		commands = getCommandsFromFile();
		mainCommands = commands.subList(getMainCommandsMinNumber(), getMainCommandsMaxNumber());
		editCommands = commands.subList(getSubCommandsMinNumber(), commands.size());
		showTasksCommands = commands.subList(getShowTasksCommandsMinNumber(), getShowTasksCommandsMaxNumber());
	}
	
	private List<String> getCommandsFromFile() throws IOException {
		FilePath path = new FilePath("src/data", "Commands.txt");
		FileIO fileIn = new FileIO();
		return fileIn.readEachLineOfFile(path.getPathToFile());
	}
	
	private int getMainCommandsMaxNumber() {
		return commands.indexOf("show all tasks");
	}
	
	private int getMainCommandsMinNumber() {
		return commands.indexOf("main menu") + 1;
	}
	
	public void getMainCommands() {
		mainCommands.stream()
					.forEach(choice -> messenger.addMessage(choice));
	}
	
	public int getMainCommandsSize() {
		return mainCommands.size();
	}
	
	private int getSubCommandsMinNumber() {
		return commands.indexOf("edit menu") + 1;
	}

	public void getEditCommands() {
		editCommands.stream()
					.forEach(choice -> messenger.addMessage(choice));
	}
	
	public int getEditCommandsSize() {
		return editCommands.size();
	} 
	
	private int getShowTasksCommandsMinNumber() {
		return commands.indexOf("show all tasks") + 1;
	}
	
	private int getShowTasksCommandsMaxNumber() {
		return commands.indexOf("edit menu");
	}
	
	public void getShowTasksCommands() {
		showTasksCommands.stream()
							.forEach(choice -> messenger.addMessage(choice));
	}
	
	public int getShowTasksCommandsSize() {
		return showTasksCommands.size();
	}
}
