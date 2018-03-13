package com.Personify.integration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileIO {	
	
	public List<String> readEachLineOfFile(Path pathToFile) throws IOException {	
		List<String> fileContents = new ArrayList<>();
		if (Files.isReadable(pathToFile)) {
			try {
				fileContents = Files.readAllLines(pathToFile);
			} catch (IOException e) {
				System.err.format("Unable to read %s. Please ensure you have correct file name and access.\n", pathToFile.getFileName());
			}
		} else {
			FileNotFoundException fileNotFound = new FileNotFoundException("\"" + pathToFile.getFileName() + "\"" + " does not exist.");
			throw fileNotFound;
		}
		return fileContents;
	}
}
