package com.Personify.integration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileIO {	
	
	public List<String> readEachLineOfFile(Path pathToFile) throws IOException {	
		List<String> fileContents = new ArrayList<>();
		if (Files.isReadable(pathToFile)) {
			fileContents = Files.readAllLines(pathToFile);
		} 
		else if (!Files.exists(pathToFile)) {
			FileNotFoundException fileNotFound = new FileNotFoundException("\"" + pathToFile.getFileName() + "\"" + " does not exist.");
			throw fileNotFound;
		} else {
			System.err.println("Unable to read \"" + pathToFile.getFileName() + "\".");
		}
		return fileContents;
	}
}
