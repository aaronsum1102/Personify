package com.Personify.base;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Motivation is an object which provide the motivational quotes. It contains a
 * default list of quote, and support addition or removal of quote from the
 * collection. There is also a function to empty the whole collection list.
 *
 * @author aaronsum
 */
public class Motivation {
    private final List<String> quotes;
    private final FilePath motivationalQuoteFile;

    /**
     * Construct motivation object with a default collection of quote.
     */
    public Motivation(final String userName) {
        motivationalQuoteFile = new FilePath("src/data", String.format("%s_motivationalQuotes.txt", userName));
        FileIO motivationalQuotesFile = new FileIO();
        quotes = motivationalQuotesFile.readEachLineOfFile(motivationalQuoteFile.getPathToFile());
    }

    /**
     * Provide complete collection of motivational quote.
     *
     * @return A collection of quote.
     */
    public List<String> getQuotes() {
        return quotes;
    }

    /**
     * Randomly return a motivational quote from the collection.
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
     * @param newQuote Element for adding into the the collection
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
     * Remove the specify element from the collection if it is in the collection.
     *
     * @param index Index of the quote to be removed.
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

    @Override
    public String toString() {
        AtomicInteger number = new AtomicInteger(1);
        String quotes = "";
        for (String quote : this.quotes) {
            quotes = quotes.concat(String.format("%d. \"%s\"\n", number.getAndIncrement(), quote));
        }
        return quotes;
    }

    public void writeMotivationalQuoteToFIle(final String userName) {
        try {
            Files.deleteIfExists(motivationalQuoteFile.getPathToFile());
        } catch (IOException e) {
            System.err.println("IO error while manipulating file.");
        }
        FilePath path = new FilePath("src/data", String.format("%s_motivationalQuotes.txt", userName));
        FileIO motivationalQuotesFileIO = new FileIO();
        motivationalQuotesFileIO.writeTaskToFile(path.getPathToFile(), quotes);
    }
}
