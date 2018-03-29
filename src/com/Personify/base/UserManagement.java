package com.Personify.base;

import com.Personify.exception.IllegalUserInfoException;
import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

import java.util.*;

/**
 * Provide functionality related to user management. Some key method include register new user into collection and edit
 * user information.
 */
public class UserManagement {
    private final HashMap<String, User> users;
    private final FilePath userFilePath;
    private final FileIO userFile;

    /**
     * Instantiate a user management object and read all user profile into system.
     */
    public UserManagement() {
        userFilePath = new FilePath("src/data", "user.txt");
        userFile = new FileIO();
        users = new HashMap<>();
        readUsersProfileFromFile();
    }

    private List<String> readFile() {
        return userFile.readEachLineOfFile(userFilePath.getPathToFile());
    }

    private void readUsersProfileFromFile() {
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
        Set<Map.Entry<String, User>> entrySet = users.entrySet();
        entrySet.forEach(entry -> {
            userInfo.add(entry.getValue().getUsername());
            userInfo.add(entry.getValue().getPassword());
        });
        return userInfo;
    }

    /**
     * Write user profile to file.
     */
    public void saveUserProfile() {
        userFile.writeTaskToFile(userFilePath.getPathToFile(), prepareUserProfileForSavingToFile());
    }

    private boolean userValidation(final String userName, final String password) {
        return users.containsKey(userName) && password.equals(users.get(userName).getPassword());
    }

    /**
     * Validate the specify password of a user match the record for the specify username.
     *
     * @param userName username of a user.
     * @param password password of as user
     * @return true if the password match the record for the specify user.
     */
    public boolean userInfoValidation(final String userName, final String password) {
        return userValidation(userName, password);
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

    /**
     * Check if the specify username is valid.
     *
     * @param userName username to check if it is valid.
     * @return true if username is not empty, unique in system and contains letters or digits only.
     * @throws IllegalUserInfoException if the username is invalid.
     */
    public boolean validateNewUser(final String userName) throws IllegalUserInfoException {
        return isUserNameInSystem(userName) && isNewUserNameValid(userName);
    }

    private boolean validateNewPassword(final String password) throws IllegalUserInfoException {
        char[] passwordChars = new char[password.length()];
        password.getChars(0, password.length(), passwordChars, 0);
        return isPasswordHasSufficientLength(password) && isPasswordLetterOrDigit(passwordChars);
    }

    /**
     * Create new user object with the specify parameters.
     *
     * @param userName username of the new user object.
     * @param password password of the new user object.
     * @throws IllegalUserInfoException If the password is invalid.
     */
    public void createUser(final String userName, final String password) throws IllegalUserInfoException {
        if (validateNewPassword(password)) {
            User newUser = new User(userName, password);
            users.put(userName, newUser);
        }
    }

    /**
     * Set the username of a user object in record with the specify username.
     *
     * @param currentUserName existing username of an user object.
     * @param newUserName     new username of an user object.
     */
    public void editUserName(final String currentUserName, final String newUserName) {
        User user = users.get(currentUserName);
        users.remove(currentUserName);
        user.setUsername(newUserName);
        users.put(newUserName, user);
    }

    /**
     * Set the password of a user object in record with the specify password.
     *
     * @param currentUserName username of an user object.
     * @param currentPassword current password for an user object.
     * @param newPassword     new password to be set for an user object.
     * @throws IllegalUserInfoException if the password does not match the specify username.
     */
    public void editPassword(final String currentUserName, final String currentPassword, final String newPassword)
            throws IllegalUserInfoException {
        if (!userInfoValidation(currentUserName, currentPassword)) {
            throw new IllegalUserInfoException("Warning: You entered password does not match the record.");
        }
        validateNewPassword(newPassword);
        users.get(currentUserName).setPassword(newPassword);
    }
}
