package com.Personify.base;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provide the motivational quotes. It contains a default list of quote, and support
 * addition or removal of quote from the collection. There is also a function to clear the whole collection list.
 *
 */
public class Motivation {
    private final static FilePath defaultPathToQuoteFile = new FilePath("src/data", "motivationalQuotes.txt");
    private final List<String> quotes;
    private FilePath motivationalQuoteFile;

    /**
     * Instantiate a <code>Motivation</code> object with a default collection of quote read from file.
     *
     * @param userName username of the user in current session.
     */
    public Motivation(final String userName) {
        motivationalQuoteFile = new FilePath("src/data", String.format("%s_motivationalQuotes.txt", userName));
        if (!Files.exists(motivationalQuoteFile.getPathToFile())) {
            motivationalQuoteFile = defaultPathToQuoteFile;
        }
        FileIO motivationalQuotesFile = new FileIO();
        quotes = motivationalQuotesFile.readEachLineOfFile(motivationalQuoteFile.getPathToFile());
    }

    /**
     * Provide complete collection of all motivational quote.
     *
     * @return A collection of quote.
     */
    public List<String> getQuotes() {
        return quotes;
    }

    /**
     * Randomly return a motivational quote selected from the collection.
     *
     * @return A motivational quote.
     */
    public String getQuote() {
        Random r = new Random();
        return quotes.get(r.nextInt(quotes.size()));
    }

    private boolean isQuoteValid(final String newQuote) {
        return newQuote.length() > 0 && !quotes.contains(newQuote) && !newQuote.isEmpty();
    }

    /**
     * Adding a new non-empty quote into the existing motivational quote collection.
     *
     * @param newQuote Element for adding into the the collection.
     * @return true if the specified element is successfully added.
     */
    public boolean addQuote(final String newQuote) {
        if (isQuoteValid(newQuote)) {
            return quotes.add(newQuote);
        } else {
            throw new IllegalArgumentException("Warning: New quotes provided is invalid.");
        }
    }

    /**
     * Remove specific element in the collection.
     *
     * @param index index of the quote to be removed.
     */
    public void removeQuote(final int index) {
        quotes.remove(index - 1);
    }

    /**
     * Remove all elements from the collection.
     *
     * @return true if the collection is empty.
     */
    public boolean removeAllQuote() {
        quotes.clear();
        return quotes.isEmpty();
    }

    /**
     * Provide all motivational quotes with numbering.
     *
     * @return all motivational quotes in a string.
     */
    @Override
    public String toString() {
        AtomicInteger number = new AtomicInteger(1);
        String quotes = "";
        for (String quote : this.quotes) {
            quotes = quotes.concat(String.format("%d. \"%s\"\n", number.getAndIncrement(), quote));
        }
        return quotes;
    }

    /**
     * Write the motivational quotes specoific to the user of current session.
     *
     * @param userName username of the user in current session.
     */
    public void writeMotivationalQuoteToFIle(final String userName) {
        try {
            if (!motivationalQuoteFile.equals(defaultPathToQuoteFile)) {
                Files.deleteIfExists(motivationalQuoteFile.getPathToFile());
            }
        } catch (IOException e) {
            System.err.println("IO error while manipulating file.");
        }
        FilePath path = new FilePath("src/data", String.format("%s_motivationalQuotes.txt", userName));
        FileIO motivationalQuotesFileIO = new FileIO();
        motivationalQuotesFileIO.writeTaskToFile(path.getPathToFile(), quotes);
    }
}
