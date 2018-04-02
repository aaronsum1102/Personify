package com.Personify.base;

import com.Personify.exception.IllegalUserInfoException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserManagementTest {
    static private Path originalPath = Paths.get("src/data", "user.txt");
    static private Path backupPath = Paths.get("src/data", "userTest.txt");
    private UserManagement userManagement;

    @BeforeAll
    static void initAll() throws IOException {
        Files.deleteIfExists(backupPath);
        Files.copy(originalPath, backupPath);
        Files.deleteIfExists(originalPath);
    }

    @AfterAll
    static void tearDownAll() throws IOException {
        Files.copy(backupPath, originalPath);
        Files.delete(backupPath);
    }

    @BeforeEach
    void setUp() {
        userManagement = new UserManagement();
    }

    @AfterEach
    void tearDown() throws IOException {
        userManagement = null;
        Files.deleteIfExists(originalPath);
    }

    @Test
    void testGetUserProfilesWithNoUsers() {
        int actualSize = userManagement.getUsersSize();
        assertEquals(0, actualSize);
    }

    @Test
    void testGetUserProfilesWithOneUser() throws IllegalUserInfoException {
        assertEquals(0, userManagement.getUsersSize());
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        userManagement.saveProfile();
        userManagement = new UserManagement();
        int actualSize = userManagement.getUsersSize();
        assertEquals(1, actualSize);
    }

    @Test
    void testSaveProfile() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        assertEquals(1, userManagement.getUsersSize());
        userManagement.saveProfile();
        userManagement = new UserManagement();
        assertEquals(1, userManagement.getUsersSize());
    }

    @Test
    void testSaveProfileWithoutUser() throws IllegalUserInfoException {
        assertEquals(0, userManagement.getUsersSize());
        userManagement.saveProfile();
        userManagement = new UserManagement();
        assertEquals(0, userManagement.getUsersSize());
    }

    @Test
    void testValidUsernameAndPassword() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        boolean actualResult = userManagement.userValidation(username, password);
        assertEquals(true, actualResult);
    }

    @Test
    void testInvalidPassword() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        boolean actualResult = userManagement.userValidation(username, "abc123");
        assertEquals(false, actualResult);
    }

    @Test
    void testInvalidUsername() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        boolean actualResult = userManagement.userValidation("todayIsSunday", password);
        assertEquals(false, actualResult);
    }

    @Test
    void testValidNewUsername() throws IllegalUserInfoException {
        String newUserName = "whileIsTheNewBlack";
        boolean actualResult = userManagement.validateNewUsername(newUserName);
        assertEquals(true, actualResult);
    }

    @Test
    void testNewUsernameWithDuplicateName() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        assertThrows(IllegalUserInfoException.class, () -> userManagement.validateNewUsername(username));
    }

    @Test
    void testNewUsernameWithSpecialSymbol() {
        String newUserName = "abc@def";
        assertThrows(IllegalUserInfoException.class, () -> userManagement.validateNewUsername(newUserName));
    }

    @Test
    void testNewUsernameWithEmptyString() {
        String newUserName = "";
        assertThrows(IllegalUserInfoException.class, () -> userManagement.validateNewUsername(newUserName));
    }

    @Test
    void testCreateNewUser() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        assertEquals(1, userManagement.getUsersSize());
    }

    @Test
    void testCreateUserWithSpecialSymbolInPassword() {
        String username = "abcd";
        String password = "abc@asd";
        assertThrows(IllegalUserInfoException.class, () -> userManagement.createUser(username, password));
    }

    @Test
    void testCreateUserWithShortPassword() {
        String username = "abcd";
        String password = "abc";
        assertThrows(IllegalUserInfoException.class, () -> userManagement.createUser(username, password));
    }

    @Test
    void testSetUsername() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        String newUsername = "bcd";
        userManagement.setUsername(username, newUsername);
        assertEquals(true, userManagement.userValidation(newUsername, password));
    }

    @Test
    void testSetUsernameWithEmptyString() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        String newUsername = "";
        assertThrows(IllegalUserInfoException.class, () -> userManagement.setUsername(username, newUsername));
    }

    @Test
    void testSetUsernameWithSpecialSymbolInUsername() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        String newUsername = "abcd@";
        assertThrows(IllegalUserInfoException.class, () -> userManagement.setUsername(username, newUsername));
    }

    @Test
    void testSetPassword() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        String newPassword = "blackIsTheNe";
        userManagement.setPassword(username, password, newPassword);
        assertEquals(true, userManagement.userValidation(username, newPassword));
    }

    @Test
    void testSetPasswordWithoutGivenCorrectCurrentPassword() throws IllegalUserInfoException {
        String username = "abcd";
        String password = "abcd12345";
        userManagement.createUser(username, password);
        String newPassword = "blackIsTheNe";
        assertThrows(IllegalUserInfoException.class, () ->
                userManagement.setPassword(username, "asdasg", newPassword));
    }

}