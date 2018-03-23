package com.Personify.textView;

import java.io.IOException;
import java.util.List;

class ShowTasksMenu extends Menu {
	List<String> showTasksMenu;
	
	public ShowTasksMenu() throws IOException {
		super();
		showTasksMenu = menus.subList(getShowTasksCommandsMinNumber(), getShowTasksCommandsMaxNumber());
	}
	
	private int getShowTasksCommandsMinNumber() {
		return menus.indexOf("show all tasks") + 1;
	}
	
	private int getShowTasksCommandsMaxNumber() {
		return menus.indexOf("edit menu");
	}
	
	public List<String> getShowTasksMenu() {
		return showTasksMenu;
	}
}
