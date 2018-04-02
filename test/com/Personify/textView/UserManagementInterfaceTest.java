package com.Personify.textView;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserManagementInterfaceTest {
    private static final Path originalPath = Paths.get("src/data", "user.txt");
    private static final Path copyPath = Paths.get("src/data", "user_copy.txt");
    private UserManagementInterface userManagementInterface;
    private ByteArrayOutputStream errContent;

    @BeforeAll
    static void initAll() throws IOException {
        Files.deleteIfExists(copyPath);
        if (Files.exists(originalPath)) {
            Files.copy(originalPath, copyPath);
            Files.deleteIfExists(originalPath);
        }
    }

    @AfterAll
    static void tearDownAll() throws IOException {
        if (Files.exists(copyPath)) {
            Files.copy(copyPath, originalPath);
            Files.delete(copyPath);
        }
    }

    @BeforeEach
    void setUp() {
        userManagementInterface = new UserManagementInterface();
        System.setIn(System.in);
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() throws IOException {
        userManagementInterface = null;
        Files.deleteIfExists(originalPath);
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    void testGetController() {
        String expectedClassName = "Controller";
        assertEquals(expectedClassName, userManagementInterface.getController().getClass().getSimpleName());
    }

    @Test
    void testGetNullUserProfile() {
        assertEquals(null, userManagementInterface.getCurrentUserProfileInUse());
    }

    @Test
    void testGetUserProfileInUseAfterLogin() {
        String input = "2\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "1\n" + "aaron\n" + "aaronsum\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String actualUserProfile = userManagementInterface.getCurrentUserProfileInUse();
        String expectedUserProfile = "aaron";
        assertEquals(expectedUserProfile, actualUserProfile);
    }

    @Test
    void testGetUserProfileAfterFailLogin() {
        String input = "2\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "1\n" + "david\n" + "aaronsum\n" + "\n"
                + "1\n" + "david\n" + "aaronsum\n" + "\n"
                + "1\n" + "david\n" + "aaronsum\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String actualUserProfile = userManagementInterface.getCurrentUserProfileInUse();
        String expectedUserProfile = "";
        assertEquals(expectedUserProfile, actualUserProfile);
    }

    @Test
    void testLogInForExistingUser() {
        String input = "1\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "2\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "1\n" + "aaron\n" + "aaronsum\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        assertEquals(true, userManagementInterface.startup());
    }

    @Test
    void testLogInForNewUser() {
        String input = "2\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "1\n" + "aaron\n" + "aaronsum\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        boolean isLogin = userManagementInterface.startup();
        assertEquals(true, isLogin);
    }

    @Test
    void testLogInFailedForMoreThan3Times() {
        String input = "1\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "1\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "1\n" + "aaron\n" + "aaronsum\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        boolean isLogin = userManagementInterface.startup();
        assertEquals(false, isLogin);
    }

    @Test
    void testErrorMessageWhenUserGiveInputOtherThanNumber() {
        String input = "test\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = "Warning: I'm smart enough to know you didn't given me a number. Don't try to challenge me.\n";
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }

    @Test
    void testErrorMessageWhenUserGiveInputAboveSelectionRange() {
        String input = "1230\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = "Warning: Command given of 1230 is not a valid selection.\n";
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }

    @Test
    void testErrorMessageWhenUserGiveInputBelowSelectionRange() {
        String input = "-1230\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = "Warning: Command given of -1230 is not a valid selection.\n";
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }

    @Test
    void testCreateAccountWithNewUsername() {
        String input = "2\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "2\n" + "david\n" + "david1234\n" + "\n"
                + "1\n" + "alex\n" + "david1234\n" + "\n"
                + "1\n" + "david\n" + "david1234\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        boolean isLogin = userManagementInterface.startup();
        assertEquals(true, isLogin);
    }

    @Test
    void testCreateAccountWithRepeatedUsername() {
        String input = "2\n" + "aaron\n" + "aaronsum\n" + "\n"
                + "2\n" + "aaron\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = String.format("\"%s\" already exists in system. Please login to your account or give me a new user name.\n", "aaron");
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }

    @Test
    void testCreateAccountWithInvalidUsername() {
        String input = "2\n" + "a@ron\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = String.format("Warning: User name of \"%s\" is invalid.\n", "a@ron");
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }

    @Test
    void testCreateAccountWithEmptyStringForUsername() {
        String input = "2\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = "Warning: Hey! You didn't type in anything.\n";
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }

    @Test
    void testCreateAccountWithNonAlphanumericPassword() {
        String input = "2\n" + "aaron\n" + "aaron@a\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = "Warning: Password is not alphanumeric.\n";
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }

    @Test
    void testCreateAccountWithShortPassword() {
        String input = "2\n" + "aaron\n" + "aaron\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = "Warning: Password length must be between 6 to 12 digit.\n";
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }

    @Test
    void testCreateAccountWithLongPassword() {
        String input = "2\n" + "aaron\n" + "everydayIsHappyDay\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n"
                + "1\n" + "\n" + "\n" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        userManagementInterface = new UserManagementInterface();
        userManagementInterface.startup();
        String expectedErrorMessage = "Warning: Password length must be between 6 to 12 digit.\n";
        assertEquals(true, errContent.toString().contains(expectedErrorMessage));
    }
}