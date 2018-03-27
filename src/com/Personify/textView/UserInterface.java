package com.Personify.textView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class UserInterface {
    final Scanner commandReader;
    final Menu menu;
    final List<String> messages;

    UserInterface() {
        commandReader = new Scanner(System.in);
        menu = new Menu();
        messages = new ArrayList<>();
    }

    void showMessagesWithHeaders() {
        final int dashLineLength = 70;
        final int appNameLength = 9;
        if (!messages.isEmpty()) {
            String upperDashLine = "";
            String lowerDashLine = "";
            int i = 0;
            while (i < dashLineLength) {
                upperDashLine = upperDashLine.concat("-");
                i++;
            }
            i = 0;
            while (i < dashLineLength * 2 + appNameLength) {
                lowerDashLine = lowerDashLine.concat("-");
                i++;
            }
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


    void isValidCommand(final int maxAllowNumber, final int userSelection) throws InvalidCommandException {
        if (!(0 < userSelection && userSelection <= maxAllowNumber)) {
            throw new InvalidCommandException(userSelection);
        }
    }
}
