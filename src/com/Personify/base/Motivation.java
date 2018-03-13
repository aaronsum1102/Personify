package com.Personify.base;
import java.io.IOException;
import java.util.*;

import com.Personify.integration.FileIO;
import com.Personify.integration.FilePath;

/**
 * Motivation is an object which provide the motivational quotes. It contains a
 * default list of quote, and support addition or removal of quote from the
 * collection. There is also a function to empty the whole collection list.
 * 
 * @author aaronsum
 * @version 1.0, 2018-03-04
 *
 */
public class Motivation {
	private List<String> quotes;
	private FileIO motivationalQuotesFile;

	/**
	 * Construct motivation object with a default collection of quote.
	 */
	public Motivation() throws IOException {
		FilePath motivationalQuoteFile = new FilePath("src/data", "motivationalQuotes.txt");
		motivationalQuotesFile = new FileIO(); 
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
	
	private boolean isQuoteValid (final String newQuote) {
		if (newQuote.length() > 0 && !quotes.contains(newQuote)) {
			return true;
		}
		return false;
	}

	/**
	 * Adding a new non-empty quote into the existing motivational quote collection.
	 * 
	 * @param newQuote
	 *            Element for adding into the the collection
	 * @return true if the specified element is successfully added.
	 */
	public boolean addQuote(final String newQuote) {
		if (isQuoteValid(newQuote)) {
			return quotes.add(newQuote);
		}
		return false;
	}

	/**
	 * Remove the specified element from the collection if it is in the collection.
	 * 
	 * @param quote
	 *            Element to be removed from collection.
	 * @return true if the specified element is in the collection.
	 */
	public boolean removeQuote(final String quote) {
		return quotes.remove(quote);
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
}
