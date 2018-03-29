package com.Personify.textView;

import com.Personify.exception.InvalidCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class UserInterface {
    final Scanner commandReader;
    private final Menu menu;
    final List<String> messages;

    UserInterface() {
        commandReader = new Scanner(System.in);
        menu = new Menu();
        messages = new ArrayList<>();
    }

    private String getDashLine(final int length) {
        String dashLine = "";
        int i = 0;
        while (i < length) {
            dashLine = dashLine.concat("-");
            i++;
        }
        return dashLine;
    }

    void showMessagesWithHeaders() {
        final int dashLineLength = 70;
        final int appNameLength = 9;
        if (!messages.isEmpty()) {
            String upperDashLine = getDashLine(dashLineLength);
            String lowerDashLine = getDashLine(dashLineLength * 2 + appNameLength);
            System.out.println(upperDashLine + "Personify" + upperDashLine);
            messages.forEach(System.out::println);
            System.out.println(lowerDashLine);
        }
        messages.clear();
    }

    void toProceed() {
        System.out.println("press \"ENTER\" to continue...");
        commandReader.nextLine();
    }

    int getCommandNoToExitAndDisplayMenu(final String menuName) {
        final int noOfElementToSubtractForFormatting = 2;
        final int commandToExit = menu.getMenu(menuName).size() - noOfElementToSubtractForFormatting;
        messages.addAll(menu.getMenu(menuName));
        showMessagesWithHeaders();
        return commandToExit;
    }

    int getUserInputSelectionAndSanityCheck(final int commandToExit) throws InvalidCommandException {
        int inputFromUser = Integer.parseInt(commandReader.nextLine());
        isValidCommand(commandToExit, inputFromUser);
        return inputFromUser;
    }

    void isValidCommand(final int maxAllowNumber, final int userSelection) throws InvalidCommandException {
        if (!(0 < userSelection && userSelection <= maxAllowNumber)) {
            throw new InvalidCommandException(userSelection);
        }
    }
}
