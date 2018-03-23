package com.Personify.textView;

import java.io.IOException;
import java.util.List;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

public class Menu {
	protected List<String> menus;
	
	public Menu() throws IOException {
		menus = getCommandsFromFile();
	}

	private List<String> getCommandsFromFile() throws IOException {
		FilePath path = new FilePath("src/data", "Commands.txt");
		FileIO fileIn = new FileIO();
		return fileIn.readEachLineOfFile(path.getPathToFile());
	}
}
