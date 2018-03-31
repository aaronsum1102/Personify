package com.Personify.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MotivationTest {
    String username;
    Motivation motivationalQuotes;
    Path userPath;

    @BeforeEach
    void setUp() {
        username = "abc";
        motivationalQuotes = new Motivation(username);
        userPath = Paths.get("src/data", "abc_motivationalQuotes.txt");
    }

    @AfterEach
    void tearDown() {
        motivationalQuotes = null;
        try {
            Files.deleteIfExists(userPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadingQuotesForNewUser() {
        try {
            Files.deleteIfExists(userPath);
            int actualQuotesSize = motivationalQuotes.getQuotes().size();
            final int defaultQuotesSize = 7;
            assertEquals(defaultQuotesSize, actualQuotesSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadingQuotesForExistingUser() {
        motivationalQuotes.addQuote("This is a test quote.");
        motivationalQuotes.writeMotivationalQuoteToFIle(username);
        motivationalQuotes = new Motivation(username);
        int actualQuotesSize = motivationalQuotes.getQuotes().size();
        final int expectedQuoteSize = 8;
        assertEquals(expectedQuoteSize, actualQuotesSize);
    }

    @Test
    void testGetQuotes() {
        motivationalQuotes.removeAllQuote();
        String expectedQuote = "Hello this is a test quote.";
        motivationalQuotes.addQuote("Hello this is a test quote.");
        String actualQuote = motivationalQuotes.getQuotes().get(0);
        assertEquals(expectedQuote, actualQuote);
        assertEquals(1, motivationalQuotes.getQuotes().size());
    }

    @Test
    void testGetRandomQuoteFromQuotesCollection() {
        boolean firstSelectionResult = motivationalQuotes.getQuotes().contains(motivationalQuotes.getQuote());
        assertEquals(true, firstSelectionResult);
        boolean secondSelectionResult = motivationalQuotes.getQuotes().contains(motivationalQuotes.getQuote());
        assertEquals(true, secondSelectionResult);
    }

    @Test
    void testAddQuoteWithNonEmptyString() {
        motivationalQuotes.removeAllQuote();
        String expectedQuote = "Hello this is a test quote.";
        boolean returnValue = motivationalQuotes.addQuote(expectedQuote);
        assertEquals(true, returnValue);
        assertEquals(true, motivationalQuotes.getQuotes().contains(expectedQuote));
    }

    @Test
    void testAddQuoteWithRepeatedQuoteInTheCollection() {
        motivationalQuotes.removeAllQuote();
        String expectedQuote = "Hello this is a test quote.";
        boolean resultOfFirstAddQuoteOperation = motivationalQuotes.addQuote(expectedQuote);
        assertEquals(true, resultOfFirstAddQuoteOperation);
        assertThrows(IllegalArgumentException.class, () -> motivationalQuotes.addQuote(expectedQuote));
        int quotesSize = motivationalQuotes.getQuotes().size();
        assertEquals(1, quotesSize);
    }

    @Test
    void testAddQuoteWithEmptyString() {
        motivationalQuotes.removeAllQuote();
        String newQuote = "";
        assertThrows(IllegalArgumentException.class, () -> motivationalQuotes.addQuote(newQuote));
        assertEquals(true, motivationalQuotes.getQuotes().isEmpty());
    }

    @Test
    void testRemoveQuoteWithQuoteInCollection() {
        motivationalQuotes.removeAllQuote();
        String newQuote = "Hello this is a test quote.";
        motivationalQuotes.addQuote(newQuote);
        assertEquals(1, motivationalQuotes.getQuotes().size());
        int taskNumber = 1;
        motivationalQuotes.removeQuote(taskNumber);
        assertEquals(true, motivationalQuotes.getQuotes().isEmpty());
    }

    @Test
    void testRemoveAllQuote() {
        boolean returnValue = motivationalQuotes.removeAllQuote();
        int quotesSize = motivationalQuotes.getQuotes().size();
        assertEquals(true, returnValue);
        assertEquals(0, quotesSize);
    }

    @Test
    void testStringRepresentationOfMotivationObject() {
        motivationalQuotes.removeAllQuote();
        String newQuote = "Hello this is a test quote.";
        motivationalQuotes.addQuote(newQuote);
        String expectedQuote = "".concat(String.format("%d. \"%s\"\n", 1, newQuote));
        assertEquals(true, motivationalQuotes.toString().equals(expectedQuote));
    }

    @Test
    void testWriteQuotesToFile() {
        motivationalQuotes.removeAllQuote();
        String newQuote = "Hello this is a test quote.";
        motivationalQuotes.addQuote(newQuote);
        assertEquals(1, motivationalQuotes.getQuotes().size());
        motivationalQuotes.writeMotivationalQuoteToFIle(username);
        motivationalQuotes = new Motivation(username);
        assertEquals(true, motivationalQuotes.getQuotes().contains(newQuote));
        assertEquals(1, motivationalQuotes.getQuotes().size());
    }
}
