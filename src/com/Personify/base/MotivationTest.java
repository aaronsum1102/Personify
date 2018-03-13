package com.Personify.base;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Automation test for Motivation class.
 * 
 * @author aaronsum
 * @version 1.0, 2018-03-04
 */
class MotivationTest {
	Motivation m;

	/**
	 * Initialize the Motivation object before each test.
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws IOException {
		m = new Motivation();
	}

	/**
	 * To test getQuotes method to ensure object is properly initialised with
	 * initial quotes.
	 */
	@Test
	void testGetQuotes() {
		int quotesSize = m.getQuotes().size();
		// by default has 7 motivational quotes in collection.
		assertEquals(7, quotesSize);
	}

	/**
	 * To test the randomly chosen element is in the collection.
	 */
	@Test
	void testGetQuote() {
		boolean result = m.getQuotes().contains(m.getQuote());
		assertEquals(true, result);
		boolean secondResult = m.getQuotes().contains(m.getQuote());
		assertEquals(true, secondResult);
	}

	/**
	 * To test the addQuote method is able to add quote with string length greater
	 * than 0.
	 */
	@Test
	void testAddQuoteValid() {
		String newQuote = "If you can't dream it, you can do it.";
		boolean returnValue = m.addQuote(newQuote);
		int quotesSize = m.getQuotes().size();
		assertEquals(true, returnValue);
		assertEquals(8, quotesSize);
	}
	
	/**
	 * To test repeated quote is not added in to collection.
	 * than 0.
	 */
	@Test
	void testAddQuoteWithRepeatedQuote() {
		String newQuote = "You just can't beat the person who never gives up.";
		boolean returnValue = m.addQuote(newQuote);
		int quotesSize = m.getQuotes().size();
		assertEquals(false, returnValue);
		assertEquals(7, quotesSize);
	}

	/**
	 * To test addQuote method does not add empty quote to collection.
	 */
	@Test
	void testAddQuoteWithEmptyQuote() {
		String newQuote = "";
		boolean returnValue = m.addQuote(newQuote);
		int quotesSize = m.getQuotes().size();
		assertEquals(false, returnValue);
		assertEquals(7, quotesSize);
	}

	/**
	 * To test removeQuote method properly remove an element if it is in the
	 * collection.
	 */
	@Test
	void testRemoveQuoteValid() {
		String quoteToRemove = "If you can dream it, you can do it.";
		boolean returnValue = m.removeQuote(quoteToRemove);
		int quotesSize = m.getQuotes().size();
		assertEquals(true, returnValue);
		assertEquals(6, quotesSize);
	}

	/**
	 * To test removeQuote method does not remove any element if it is not in the
	 * collection.
	 */
	@Test
	void testRemoveQuoteInvalid() {
		String quoteToRemove = "If you can dream it.";
		boolean returnValue = m.removeQuote(quoteToRemove);
		int quotesSize = m.getQuotes().size();
		assertEquals(false, returnValue);
		assertEquals(7, quotesSize);
	}

	/**
	 * To test removeAllQuote method remove all elements in the collection.
	 */
	@Test
	void testRemoveAllQuote() {
		boolean returnValue = m.removeAllQuote();
		int quotesSize = m.getQuotes().size();
		assertEquals(true, returnValue);
		assertEquals(0, quotesSize);
	}

}
