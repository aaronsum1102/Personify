package com.Personify.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileIOTest {
    private final ByteArrayOutputStream errOut = new ByteArrayOutputStream();
    private FileIO file;

    @BeforeEach
    void setUp() {
        file = new FileIO();
        System.setErr(new PrintStream(errOut));
    }

    @AfterEach
    void tearDown() {
        file = null;
        System.setErr(System.err);
    }

    @Test
    void testReadingFileFromSystem() throws IOException {
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwx------");
        Path filePath = Paths.get("src/data", "fileForTesting.txt");
        Files.createFile(filePath, PosixFilePermissions.asFileAttribute(perms));
        int fileContentSize = file.readEachLineOfFile(filePath).size();
        final int CORRECT_CONTENT_SIZE = 0;
        Files.delete(filePath);
        assertEquals(CORRECT_CONTENT_SIZE, fileContentSize);
    }

    @Test
    void testReadingFileNotExistsInSystem() throws IOException {
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwx------");
        Path filePath = Paths.get("src/data", "fileForTesting.txt");
        Files.createFile(filePath, PosixFilePermissions.asFileAttribute(perms));
        Files.delete(filePath);
        String expectedResult = "IOException: java.io.FileNotFoundException: " + "\"" + filePath.getFileName() + "\"" + " does not exist.";
        file.readEachLineOfFile(filePath);
        assertEquals(true, errOut.toString().contains(expectedResult));
    }

    @Test
    void testReadingFileWithNoReadPermission() throws IOException {
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("-wx------");
        Path filePath = Paths.get("src/data", "fileForTesting.txt");
        Files.createFile(filePath, PosixFilePermissions.asFileAttribute(perms));
        file.readEachLineOfFile(filePath).size();
        String expectedResult = "Unable to read \"" + filePath.getFileName() + "\".\n";
        Files.delete(filePath);
        assertEquals(expectedResult, errOut.toString());
    }

    @Test
    void testWritingFileToSystem() throws IOException {
        List<String> infoToWriteToFile = new ArrayList<>();
        infoToWriteToFile.add("hello world");
        Path filePath = Paths.get("test.txt");
        file.writeTaskToFile(filePath, infoToWriteToFile);
        List<String> infoReadFromFile = file.readEachLineOfFile(filePath);
        Files.delete(filePath);
        assertEquals(infoToWriteToFile, infoReadFromFile);
    }
}
