package com.Personify.integration;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Provide methods to get the system path of a file.
 */
public class FilePath {
    private final Path PATH_TO_FILE;

    /**
     * Construct <code>FilePath</code> object with the specify folder path and file name.
     * @param folderName path to the folder where the file is at.
     * @param fileName file name of the file.
     */
    public FilePath(final String folderName, final String fileName) {
        if (folderName.isEmpty() || fileName.isEmpty()) {
            throw new IllegalArgumentException("No empty path name is allowed.");
        } else {
            PATH_TO_FILE = Paths.get(folderName, fileName);
        }
    }

    /**
     * Provide a <code>Path</code> object for representation of the system path to file.
     * @return a <code>Path</code> object for this object.
     */
    public Path getPathToFile() {
        return PATH_TO_FILE;
    }
}
