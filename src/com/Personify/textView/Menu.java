package com.Personify.textView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

class Menu {
    private List<String> menuDetails;
    private HashMap<String, List<String>> menus;

    Menu() throws IOException {
        menuDetails = getCommandsFromFile();
        menus = new HashMap<>();
        addMenuToMenus();
    }

    private List<String> getCommandsFromFile() throws IOException {
        FilePath path = new FilePath("src/data", "Commands.txt");
        FileIO fileIn = new FileIO();
        return fileIn.readEachLineOfFile(path.getPathToFile());
    }

    private void addMenuToMenus() {
        final int noToOffsetForStartOfMenu = 2;
        final int noToOffsetForMenuName = 1;
        int startIndex = 0;

        for (int i = 0; i < menuDetails.size(); i++) {
            if (menuDetails.get(i).equals("+")) {
                startIndex = i;
            } else if (menuDetails.get(i).equals("-")) {
                int fromIndex = startIndex + noToOffsetForStartOfMenu;
                String key = menuDetails.get(startIndex + noToOffsetForMenuName);
                menus.put(key, menuDetails.subList(fromIndex, i));
            }
        }
    }

    List<String> getMenu(String menuName) {
        return menus.get(menuName);
    }
}
