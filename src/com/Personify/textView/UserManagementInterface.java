package com.Personify.textView;

import com.Personify.base.IllegalUserInfoException;
import com.Personify.controller.Controller;

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

    public boolean startup() {
        boolean isEnding = false;
        boolean isLogIn = false;
        int logInCounter = 1;
        while (!isEnding) {
            try {
                int userChoices;
                if (logInCounter >= 4) {
                    System.err.println("Warning: Maximum attempt for login exceeded!");
                    System.exit(0);
                }
                messages.addAll(menu.getMenu("startup"));
                showMessagesWithHeaders();

                userChoices = Integer.parseInt(commandReader.nextLine());
                isValidCommand(3, userChoices);

                switch (userChoices) {
                    case 1:
                        isLogIn = logIn();
                        if (isLogIn) {
                            isEnding = true;
                        } else {
                            logInCounter++;
                            System.err.println("Warning: Invalid user name or password.");
                        }
                        break;
                    case 2:
                        createUser();
                        System.out.println("Congrats! You can log in the system now.");
                        logInCounter = 1;
                        break;
                    case 3:
                        System.out.println("Good bye!");
                        commandReader.close();
                        System.exit(0);
                        break;
                }
                toProceed();
            } catch (NumberFormatException e) {
                System.err.println("Warning: I'm smart enough to know you didn't given me a number. Don't try to challenge me.");
                toProceed();
            } catch (InvalidCommandException | IllegalUserInfoException e) {
                System.err.println(e.getMessage());
                toProceed();
            } finally {
                controller.saveUserProfile();
            }
        }
        return isLogIn;
    }
}
