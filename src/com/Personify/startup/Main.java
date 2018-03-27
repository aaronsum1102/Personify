package com.Personify.startup;

import com.Personify.textView.OperationUserInterface;
import com.Personify.textView.UserManagementInterface;

/**
 * Initialized the Personify program.
 *
 * @author aaronsum
 * @version 2.0, 2018-03-13
 */
class Main {

    /**
     * Startup the system operation loop.
     *
     * @param args There are no command line expected.
     */
    public static void main(String[] args) {
        UserManagementInterface userManagementInterface = new UserManagementInterface();
        if (userManagementInterface.startup()) {
            String currentUser = userManagementInterface.getCurrentUserProfileInUse();
            OperationUserInterface view = new OperationUserInterface(userManagementInterface.getController(), currentUser);
            view.operation();
        }
    }
}
