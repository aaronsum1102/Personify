package com.Personify.startup;

import com.Personify.textView.OperationUserInterface;
import com.Personify.textView.UserManagementInterface;

/**
 * Initialized the Personify program.
 */
class Main {

    /**
     * Construct {@link UserManagementInterface} object and call method {@link UserManagementInterface#startup()}
     * method to initialised user login or registration process. If login is successful it will construct
     * {@link OperationUserInterface} and call {@link OperationUserInterface#operation()} method for the operations
     * in the program.
     *
     * @param args There are no command line expected.
     */
    public static void main(String[] args) {
        UserManagementInterface userManagementInterface = new UserManagementInterface();
        if (userManagementInterface.startup()) {
            String currentUser = userManagementInterface.getCurrentUserProfileInUse();
            OperationUserInterface view = new OperationUserInterface(userManagementInterface.getController(),
                    currentUser);
            view.operation();
        }
    }
}
