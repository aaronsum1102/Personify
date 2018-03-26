package com.Personify.integration;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class FileIO {	
	
	public List<String> readEachLineOfFile(Path pathToFile) throws IOException {	
		List<String> fileContents = new ArrayList<>();
		if (Files.isReadable(pathToFile)) {
			fileContents = Files.readAllLines(pathToFile);
		} 
		else if (!Files.exists(pathToFile)) {
			throw new FileNotFoundException("\"" + pathToFile.getFileName() + "\"" + " does not exist.");
		} else {
			System.err.println("Unable to read \"" + pathToFile.getFileName() + "\".");
		}
		return fileContents;
	}

	public void writeTaskToFile(final Path pathToFile, List<String> information) {
		Charset charset = Charset.forName("UTF-8");
		try (BufferedWriter writer = Files.newBufferedWriter(pathToFile, charset)) {
			for (String info : information) {
				writer.write(info);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}

}
