package com.Personify.textView;

import java.io.IOException;
import java.util.List;

class EditTaskMenu extends Menu {
	List<String> editTaskMenu;
	
	public EditTaskMenu() throws IOException {
		super();
		editTaskMenu = menus.subList(getSubCommandsMinNumber(), menus.size());
	}
	
	private int getSubCommandsMinNumber() {
		return menus.indexOf("edit menu") + 1;
	}
	
	public List<String> getEditTaskMenu() {
		return editTaskMenu;
	} 

}
