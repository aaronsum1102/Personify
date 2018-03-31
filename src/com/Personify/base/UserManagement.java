package com.Personify.base;

import com.Personify.exception.IllegalUserInfoException;
import com.Personify.integration.FileIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;

/**
 * Provide functionality related to user management. Some key method include register new user into collection and edit
 * user information.
 */
public class UserManagement {
    private final HashMap<String, String> users;
    private final Path path;
    private final FileIO userFile;

    /**
     * Instantiate a user management object and read all user profile into system.
     */
    public UserManagement() {
        path = Paths.get("src/data", "user.txt");
        userFile = new FileIO();
        users = new HashMap<>();
        readProfiles();
    }

    public int getUsersSize() {
        return users.size();
    }

    private List<String> readFile() {
        return userFile.readEachLineOfFile(path);
    }

    private void readProfiles() {
        if (!Files.exists(path)) {
            try {
                Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");
                FileAttribute fileAttribute = PosixFilePermissions.asFileAttribute(perms);
                Files.createFile(path, fileAttribute);
            } catch (IOException e) {
                System.err.println("IO error when accessing file.");
            }
        } else {
            List<String> usersInfo = readFile();
            usersInfo.forEach(userInfo -> {
                Scanner sc = new Scanner(userInfo);
                sc.useDelimiter(";");
                users.put(sc.next(), sc.next());
            });
        }
    }

    private List<String> prepareProfileForSaving() {
        List<String> userInfo = new ArrayList<>();
        users.forEach((key, value) -> userInfo.add(key + ";" + value));
        return userInfo;
    }

    /**
     * Write user profile to file.
     */
    public void saveProfile() {
        userFile.writeTaskToFile(path, prepareProfileForSaving());
    }


    private boolean validation(final String username, final String password) {
       return users.containsKey(username) && users.get(username).equals(password);
    }

    /**
     * Validate the specify password of a user match the record for the specify username.
     *
     * @param userName username of a user.
     * @param password password of as user
     * @return true if the password match the record for the specify user.
     */
    public boolean userValidation(final String userName, final String password) {
        return validation(userName, password);
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
        if (!(password.length() >= minimumPasswordLength && password.length() <= maximumPasswordLength)) {
            throw new IllegalUserInfoException(String.format("Warning: Password length must be between %d to %d.",
                    minimumPasswordLength, maximumPasswordLength));
        }
        return true;
    }

    private boolean isUsernameValid(final String userName) throws IllegalUserInfoException {
        for (int i = 0; i < userName.length(); i++) {
            if (!Character.isLetterOrDigit(userName.charAt(i))) {
                throw new IllegalUserInfoException(String.format("Warning: User name of \"%s\" is invalid.", userName));
            }
        }
        if (userName.isEmpty()) throw new IllegalUserInfoException("Warning: Hey! You didn't type in anything.");
        return true;
    }

    private boolean isUsernameUnique(final String username) throws IllegalUserInfoException {
        if (users.containsKey(username)) {
            throw new IllegalUserInfoException(String.format("\"%s\" already exists in system. Please login to your" +
                    " account or give me a new user name.", username));
        }
        return true;
    }

    /**
     * Check if the specify new username is valid for the specific user.
     *
     * @param userName username to check if it is valid.
     * @return true if username is not empty, unique in system and contains letters or digits only.
     * @throws IllegalUserInfoException if the specify username is invalid.
     */
    public boolean validateNewUsername(final String userName) throws IllegalUserInfoException {
        return isUsernameUnique(userName) && isUsernameValid(userName);
    }

    private boolean validateNewPassword(final String password) throws IllegalUserInfoException {
        char[] passwordChars = new char[password.length()];
        password.getChars(0, password.length(), passwordChars, 0);
        return isPasswordHasSufficientLength(password) && isPasswordLetterOrDigit(passwordChars);
    }

    /**
     * Create new user object with the specify parameters. The password needs to be between 6 to 12 digits or contains
     * letter or digits only.
     *
     * @param username username of the new user object.
     * @param password password of the new user object.
     * @throws IllegalUserInfoException if the password is invalid.
     */
    public void createUser(final String username, final String password) throws IllegalUserInfoException {
        if (validateNewPassword(password)) {
            users.put(username, password);
        }
    }

    /**
     * Set the username of a user object in record with the specify username.
     *
     * @param currentUsername existing username of an user object.
     * @param newUsername     new username of an user object.
     */
    public void setUsername(final String currentUsername, final String newUsername) throws IllegalUserInfoException {
        if (validateNewUsername(newUsername)) {
            String currentPassword = users.get(currentUsername);
            users.remove(currentUsername);
            users.put(newUsername, currentPassword);
        }
    }

    /**
     * Set the password of a user object in record with the specify password.
     *
     * @param currentUserName username of an user object.
     * @param currentPassword current password for an user object.
     * @param newPassword     new password to be set for an user object.
     * @throws IllegalUserInfoException if the password does not match the specify username.
     */
    public void setPassword(final String currentUserName, final String currentPassword, final String newPassword)
            throws IllegalUserInfoException {
        if (!userValidation(currentUserName, currentPassword)) {
            throw new IllegalUserInfoException("Warning: You entered password does not match the record.");
        }
        validateNewPassword(newPassword);
        users.put(currentUserName, newPassword);
    }
}
