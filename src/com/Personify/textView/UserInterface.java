package com.Personify.textView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class UserInterface {
    Scanner commandReader;
    Menu menu;
    List<String> messages;

    UserInterface() throws IOException {
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
            while(i < dashLineLength) {
                upperDashLine += "-";
                i++;
            }
            i = 0;
            while(i < dashLineLength * 2 + appNameLength) {
                lowerDashLine += "-";
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
