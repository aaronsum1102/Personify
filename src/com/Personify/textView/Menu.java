package com.Personify.textView;

import com.Personify.integration.FileIO;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * Provide methods to load menus use in operation from files into system.
 */
class Menu {
    private final List<String> menuDetails;
    private final HashMap<String, List<String>> menus;

    /**
     * Construct <code>Menu</code> object and load menus from file into system.
     */
    Menu() {
        menuDetails = getCommandsFromFile();
        menus = new HashMap<>();
        addMenuToMenus();
    }

    private List<String> getCommandsFromFile() {
        Path path = Paths.get("src/data", "Commands.txt");
        FileIO fileIn = new FileIO();
        return fileIn.readEachLineOfFile(path);
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

    /**
     * Provide the menu content based on specific menu name.
     *
     * @param menuName name of the menu.
     * @return a collection of the String content of the menu.
     */
    List<String> getMenu(final String menuName) {
        return menus.get(menuName);
    }
}
