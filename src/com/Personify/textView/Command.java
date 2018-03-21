package com.Personify.textView;

import java.io.IOException;
import java.util.List;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

public class Command {
	private List<String> commands;
	private List<String> mainCommands;
	private List<String> editCommands;
	
	public Command() throws IOException {
		commands = getCommandsFromFile();
		mainCommands = commands.subList(getMainCommandsMinNumber(), getMainCommandsMaxNumber());
		editCommands = commands.subList(getSubCommandsMinNumber(), commands.size());
	}
	
	private List<String> getCommandsFromFile() throws IOException {
		FilePath path = new FilePath("src/data", "Commands.txt");
		FileIO fileIn = new FileIO();
		return fileIn.readEachLineOfFile(path.getPathToFile());
	}
	
	private int getMainCommandsMaxNumber() {
		return commands.indexOf("edit menu");
	}
	
	private int getMainCommandsMinNumber() {
		return commands.indexOf("main menu") + 1;
	}
	
	public List<String> getMainCommands() {
		return mainCommands;
	}
	
	private int getSubCommandsMinNumber() {
		return commands.indexOf("edit menu") + 1;
	}

	public List<String> getEditCommands() {
		return editCommands;
	}
}
