package com.Personify.integration;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePath {
	private final Path PATH_TO_FILE;
	
	public FilePath(String folderName, String fileName) {
		if (folderName.isEmpty() || fileName.isEmpty()) {
			throw new IllegalArgumentException("No empty path name is allowed.");
		} else {
			PATH_TO_FILE = Paths.get(folderName, fileName);
		}
	}
	
	public Path getPathToFile() {
		return PATH_TO_FILE;
	}
}
