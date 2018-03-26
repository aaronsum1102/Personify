package com.Personify.base;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

import java.io.IOException;
import java.util.*;

public class UserManagement {
    private HashMap<String, User> users;
    private FilePath userFilePath;
    private FileIO userFile;

    public UserManagement() throws IOException {
        userFilePath = new FilePath("src/data", "user.txt");
        userFile = new FileIO();
        users = new HashMap<>();
        readUsersProfileFromFile();
    }

    private List<String> readFile() throws IOException {
        return userFile.readEachLineOfFile(userFilePath.getPathToFile());
    }

    private void readUsersProfileFromFile() throws IOException {
        Iterator it = readFile().iterator();
        while (it.hasNext()) {
            String userName = it.next().toString();
            String password = it.next().toString();
            User user = new User(userName, password);
            users.put(userName, user);
        }
    }

    private List<String> prepareUserProfileForSavingToFile() {
        List<String> userInfo = new ArrayList<>();
        Set userNameSet = users.keySet();
        Iterator it = userNameSet.iterator();
        while (it.hasNext()) {
            String userName = it.next().toString();
            userInfo.add(userName);
            userInfo.add(users.get(userName).getPassword());
        }
        return userInfo;
    }

    public void saveUserProfile() {
       userFile.writeTaskToFile(userFilePath.getPathToFile(), prepareUserProfileForSavingToFile());
    }

    private boolean loginValidation(final String userName, final String password) {
        return users.containsKey(userName) && password.equals(users.get(userName).getPassword());
    }

    public boolean logIn(final String userName, final String password) {
        return loginValidation(userName, password);
    }

    private boolean isPasswordLetterOrDigit(final char[] chars) throws IllegalUserInfoException {
        for (char character : chars) {
            if (!Character.isLetterOrDigit(character)) {
                throw new IllegalUserInfoException("Warning: Password is not alphanumeric.");
            }
        }
        return true;
    }

    private boolean isPasswordHasSufficientLength(final String password) throws IllegalUserInfoException {
        final int minimumPasswordLength = 6;
        final int maximumPasswordLength = 12;
        if (!(password.length() >= minimumPasswordLength && password.length() < maximumPasswordLength)) {
            throw new IllegalUserInfoException(String.format("Warning: Password length must be between %d to %d.",
                                                            minimumPasswordLength, maximumPasswordLength));
        }
        return true;
    }

    private boolean isNewUserNameValid(final String userName) throws IllegalUserInfoException {
        for (int i = 0; i < userName.length(); i++) {
            if (!Character.isLetterOrDigit(userName.charAt(i))) {
                throw new IllegalUserInfoException(String.format("Warning: User name of \"%s\" is invalid.", userName));
            }
        }
        if (userName.isEmpty()) throw new IllegalUserInfoException("Warning: Hey! You didn't type in anything.");
        return true;
    }

    private boolean isUserNameInSystem(final String userName) throws IllegalUserInfoException {
        if (users.containsKey(userName)) {
            throw new IllegalUserInfoException(String.format("\"%s\" already exists in system. Please login to your" +
                                                            " account or give me a new user name.", userName));
        }
        return true;
    }

    public boolean validateNewUser(final String userName) throws IllegalUserInfoException {
        return isUserNameInSystem(userName) && isNewUserNameValid(userName);
    }

    private boolean validateNewPassword(final String password) throws IllegalUserInfoException {
        char[] passwordChars = new char[password.length()];
        password.getChars(0, password.length(), passwordChars, 0);
        return isPasswordHasSufficientLength(password) && isPasswordLetterOrDigit(passwordChars);
    }

    public void createUser(final String userName, final String password) throws IllegalUserInfoException {
        if (validateNewPassword(password)) {
            User newUser = new User(userName, password);
            users.put(userName, newUser);
        }
    }
}
