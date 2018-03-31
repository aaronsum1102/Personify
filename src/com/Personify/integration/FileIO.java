package com.Personify.integration;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods to read data from file and write data to file.
 */
public class FileIO {
    /**
     * Read each line of the information from the specify <code>Path</code> object as a collection of String if the file
     * is exists and readable.
     *
     * @param path <code>Path</code> object which contains information of the path to target file.
     * @return a collection of string read from file.
     */
    public List<String> readEachLineOfFile(final Path path) {
        List<String> fileContents = new ArrayList<>();
        try {
            if (Files.isReadable(path)) {
                fileContents = Files.readAllLines(path);
            } else if (!Files.exists(path)) {
                throw new FileNotFoundException("\"" + path.getFileName() + "\"" + " does not exist.");
            } else {
                System.err.println("Unable to read \"" + path.getFileName() + "\".");
            }
        } catch (IOException e) {
           System.err.format("IOException: %s%n", e);
        }
        return fileContents;
    }

    /**
     * Write the specific collection of information to the specify path.
     *
     * @param path  <code>Path</code> object which contains information of the path to target file.
     * @param information a collection of String to be written to file.
     */
    public void writeTaskToFile(final Path path, final List<String> information) {
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            for (String info : information) {
                writer.write(info);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

}
