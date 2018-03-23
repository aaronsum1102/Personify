package com.Personify.textView;

import java.io.IOException;
import java.util.List;

class MainMenu extends Menu{
	List<String> mainMenu;
	
	public MainMenu() throws IOException {
		super();
		mainMenu = menus.subList(getMainCommandsMinNumber(), getMainCommandsMaxNumber());
	}
	
	private int getMainCommandsMaxNumber() {
		return menus.indexOf("show all tasks");
	}
	
	private int getMainCommandsMinNumber() {
		return menus.indexOf("main menu") + 1;
	}
	
	public List<String> getMainMenu() {
		return mainMenu;
	}
}
