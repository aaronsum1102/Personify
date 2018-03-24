package com.Personify.textView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

public class Menu {
	protected List<String> menuDetails;
	private HashMap<String, List<String>> menus;
	
	public Menu() throws IOException {
		menuDetails = getCommandsFromFile();
		menus = new HashMap<>();
		addMenuToMenus();
	}
	
	private List<String> getCommandsFromFile() throws IOException {
		FilePath path = new FilePath("src/data", "Commands.txt");
		FileIO fileIn = new FileIO();
		return fileIn.readEachLineOfFile(path.getPathToFile());
	}
	
	protected List<String> getMenuDetails() {
		return menuDetails;
	}
	
	private void addMenuToMenus() throws IOException {
		String key = "";
		int startIndex = 0;
		int fromIndex = 0;
		int toIndex = 0;
		final int noToOffsetForStartOfMenu = 2;
		final int noToOffesetForMenuName = 1;
		
		for (int i = 0; i < menuDetails.size(); i++) {
			if(menuDetails.get(i).equals("+")) {
				startIndex = i;
			} else if (menuDetails.get(i).equals("-")) {
				toIndex = i;
				fromIndex = startIndex + noToOffsetForStartOfMenu;
				key = menuDetails.get(startIndex + noToOffesetForMenuName);
				menus.put(key, menuDetails.subList(fromIndex , toIndex));
			}
		}
	}
	
	public List<String> getMenu(String menuName) {
		return menus.get(menuName);
	}
}
