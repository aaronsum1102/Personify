package com.Personify.textView;

import com.Personify.base.IllegalUserInfoException;
import com.Personify.controller.Controller;
import com.Personify.integration.StatusTracker;

public class UserManagementInterface extends UserInterface {
    private final Controller controller;
    private String currentUserProfileInUse;

    public UserManagementInterface() {
        controller = new Controller();
    }

    public Controller getController() {
        return controller;
    }

    public String getCurrentUserProfileInUse() {
        return currentUserProfileInUse;
    }

    private boolean logIn() {
        System.out.print("Username: ");
        String userName = commandReader.nextLine();
        currentUserProfileInUse = userName;
        System.out.print("Password: ");
        String password = commandReader.nextLine();
        return controller.userInfoValidation(userName, password);
    }

    private void createUser() throws IllegalUserInfoException {
        System.out.print("Username: ");
        String userName = commandReader.nextLine();
        controller.validateNewUserName(userName);
        System.out.println("\nHint: Password should be alphanumeric and contains minimum 6 characters.");
        System.out.print("Password: ");
        String password = commandReader.nextLine();
        controller.createUser(userName, password);
    }

    private void switchLoginOptions(final int userChoices, final StatusTracker<Boolean, Integer> tracker)
            throws IllegalUserInfoException {
        switch (userChoices) {
            case 1:
                Boolean isLogin = logIn();
                if (isLogin) {
                    tracker.setStatus(isLogin);
                } else {
                    Integer counter = tracker.getTrackerIndex();
                    tracker.setTrackerIndex(++counter);
                    System.err.println("Warning: Invalid user name or password.");
                }
                break;
            case 2:
                createUser();
                System.out.println("Congrats! You can log in the system now.");
                tracker.setTrackerIndex(1);
                break;
            case 3:
                System.out.println("Good bye!");
                commandReader.close();
                System.exit(0);
                break;
        }
    }

    public boolean startup() {
        boolean isLogin = false;
        int logInCounter = 1;
        StatusTracker<Boolean, Integer> tracker = new StatusTracker<>(isLogin, logInCounter);
        while (!isLogin) {
            if (tracker.getTrackerIndex() >= 4) {
                System.err.println("Warning: Maximum attempt for login exceeded!");
                System.exit(0);
            }
            try {
                final String menuName = "startup";
                final int commandToExit = getCommandNoToExitAndDisplayMenu(menuName);
                int userChoices = getUserInputSelectionAndSanityCheck(commandToExit);
                switchLoginOptions(userChoices, tracker);
                isLogin = tracker.getStatus();
            } catch (NumberFormatException e) {
                System.err.println("Warning: I'm smart enough to know you didn't given me a number. Don't try to challenge me.");
            } catch (InvalidCommandException | IllegalUserInfoException e) {
                System.err.println(e.getMessage());
            } finally {
                toProceed();
                controller.saveUserProfile();
            }
        }
        return tracker.getStatus();
    }
}
