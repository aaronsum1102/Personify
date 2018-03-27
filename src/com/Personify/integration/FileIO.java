package com.Personify.integration;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    public List<String> readEachLineOfFile(final Path pathToFile) {
        List<String> fileContents = new ArrayList<>();
        try {
            if (Files.isReadable(pathToFile)) {
                fileContents = Files.readAllLines(pathToFile);
            } else if (!Files.exists(pathToFile)) {
                throw new FileNotFoundException("\"" + pathToFile.getFileName() + "\"" + " does not exist.");
            } else {
                System.err.println("Unable to read \"" + pathToFile.getFileName() + "\".");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return fileContents;
    }

    public void writeTaskToFile(final Path pathToFile, final List<String> information) {
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
