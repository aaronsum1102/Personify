package com.Personify.base;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MotivationTest {
	private Motivation motivationalQuotes;

	@BeforeEach
	void setUp() throws IOException {
		motivationalQuotes = new Motivation();
	}

	@AfterEach
	void tearDown() {
		motivationalQuotes = null;
	}

	@Test
	void testQuotesAreReadProperlyFromFile() {
		int quotesSize = motivationalQuotes.getQuotes().size();
		final int DEFAULT_QUOTES_SIZE = 7;
		assertEquals(DEFAULT_QUOTES_SIZE, quotesSize);
	}

	@Test
	void testRandomSelectionFromQuotes() {
		boolean firstSelectionResult = motivationalQuotes.getQuotes().contains(motivationalQuotes.getQuote());
		assertEquals(true, firstSelectionResult);
		boolean secondSelectionResult = motivationalQuotes.getQuotes().contains(motivationalQuotes.getQuote());
		assertEquals(true, secondSelectionResult);
	}

	@Test
	void testAddQuoteWithStringLengthLongerThanZero() {
		String newQuote = "If you can't dream it, you can do it.";
		boolean returnValue = motivationalQuotes.addQuote(newQuote);
		int quotesSize = motivationalQuotes.getQuotes().size();
		assertEquals(true, returnValue);
		assertEquals(8, quotesSize);
	}

	@Test
	void testAddQuoteWithRepeatedQuoteInTheCollection() {
		String newQuote = "You just can't beat the person who never gives up.";
		boolean returnValue = motivationalQuotes.addQuote(newQuote);
		int quotesSize = motivationalQuotes.getQuotes().size();
		assertEquals(false, returnValue);
		assertEquals(7, quotesSize);
	}

	@Test
	void testAddQuoteWithEmptyString() {
		String newQuote = "";
		boolean returnValue = motivationalQuotes.addQuote(newQuote);
		int quotesSize = motivationalQuotes.getQuotes().size();
		assertEquals(false, returnValue);
		assertEquals(7, quotesSize);
	}

	@Test
	void testRemoveQuoteWithQuoteInExistingCollection() {
		String quoteToRemove = "If you can dream it, you can do it.";
		boolean returnValue = motivationalQuotes.removeQuote(quoteToRemove);
		int quotesSize = motivationalQuotes.getQuotes().size();
		assertEquals(true, returnValue);
		assertEquals(6, quotesSize);
	}

	@Test
	void testRemoveQuoteWithQuoteNotInExistingCollection() {
		String quoteToRemove = "If you can dream it.";
		boolean returnValue = motivationalQuotes.removeQuote(quoteToRemove);
		int quotesSize = motivationalQuotes.getQuotes().size();
		assertEquals(false, returnValue);
		assertEquals(7, quotesSize);
	}

	@Test
	void testRemoveQuoteWithEmptyString() {
		String quoteToRemove = "";
		boolean returnValue = motivationalQuotes.removeQuote(quoteToRemove);
		int quotesSize = motivationalQuotes.getQuotes().size();
		assertEquals(false, returnValue);
		assertEquals(7, quotesSize);
	}

	@Test
	void testRemoveAllQuote() {
		boolean returnValue = motivationalQuotes.removeAllQuote();
		int quotesSize = motivationalQuotes.getQuotes().size();
		assertEquals(true, returnValue);
		assertEquals(0, quotesSize);
	}

}
