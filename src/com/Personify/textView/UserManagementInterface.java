package com.Personify.textView;

import com.Personify.exception.IllegalUserInfoException;
import com.Personify.controller.Controller;
import com.Personify.exception.InvalidCommandException;
import com.Personify.util.InformationPair;

import java.io.Console;

/**
 * Manage the login or registration process for a user.
 */
public class UserManagementInterface extends UserInterface {
    private final Controller controller;
    private String currentUserProfileInUse;

    /**
     * Construct <code>UserManagementInterface</code> object and <code>Controller</code> object.
     */
    public UserManagementInterface() {
        controller = new Controller();
    }

    /**
     * Provide a <code>Controller</code> object.
     *
     * @return a <code>Controller</code> object.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Provide the username of the user in current session.
     *
     * @return username of the user in current session.
     */
    public String getCurrentUserProfileInUse() {
        return currentUserProfileInUse;
    }

    private boolean logIn() {
        Console console = System.console();
        System.out.print("Username: ");
        String userName = commandReader.nextLine();
        currentUserProfileInUse = userName;
        String password = String.copyValueOf(console.readPassword("Password: "));
//        System.out.print("Password: ");
//        String password = commandReader.nextLine();
        return controller.userInfoValidation(userName, password);
    }

    private void createUser() throws IllegalUserInfoException {
        Console console = System.console();
        System.out.print("Username: ");
        String userName = commandReader.nextLine();
        controller.validateNewUserName(userName);
        System.out.println("\nHint: Password should be alphanumeric and contains minimum 6 characters.");
        //System.out.print("Password: ");
        String password = String.copyValueOf(console.readPassword("Password: "));
        //String password = commandReader.nextLine();
        controller.createUser(userName, password);
    }

    private void switchLoginOptions(final int userChoices, final InformationPair<Boolean, Integer> tracker)
            throws IllegalUserInfoException {
        switch (userChoices) {
            case 1:
                Boolean isLogin = logIn();
                if (isLogin) {
                    tracker.setKey(isLogin);
                } else {
                    currentUserProfileInUse = "";
                    Integer counter = tracker.getValue();
                    tracker.setValue(++counter);
                    System.err.println("Warning: Invalid user name or password.");
                }
                break;
            case 2:
                createUser();
                System.out.println("Congrats! You can log in the system now.");
                tracker.setValue(1);
                break;
            case 3:
                System.out.println("Good bye!");
                System.exit(0);
                break;
        }
    }

    /**
     * Operation for user to login or add new account in the system. User have maximum of 3 attempt to login. If the user
     * input wrong username or password for more than 3 times, the program will end by itself.
     *
     * @return true if the user successfully login to system.
     */
    public boolean startup() {
        boolean isLogin = false;
        int logInCounter = 1;
        InformationPair<Boolean, Integer> tracker = new InformationPair<>(isLogin, logInCounter);
        while (!isLogin) {
            if (tracker.getValue() >= 4) {
                System.err.println("Warning: Maximum attempt for login exceeded!");
                tracker.setKey(false);
                break;
            }
            try {
                final String menuName = "startup";
                final int commandToExit = getCommandNoToExitAndDisplayMenu(menuName);
                int userChoices = getUserInputSelectionAndSanityCheck(commandToExit);
                switchLoginOptions(userChoices, tracker);
                isLogin = tracker.getKey();
            } catch (NumberFormatException e) {
                System.err.println("Warning: I'm smart enough to know you didn't given me a number. Don't try to challenge me.");
            } catch (InvalidCommandException | IllegalUserInfoException e) {
                System.err.println(e.getMessage());
            } finally {
                toProceed();
                controller.saveUserProfile();
            }
        }
        return tracker.getKey();
    }
}
