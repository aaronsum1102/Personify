import java.util.*;

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
	private ArrayList<String> quotes;

	/**
	 * Construct motivation object with a default collection of quote.
	 */
	public Motivation() {
		quotes = new ArrayList<>();
		quotes.addAll(Arrays.asList("If you can dream it, you can do it.",
				"The future belongs to those who believe in the beauty of their dreams.",
				"Aim for the moon. If you miss, you may hit a star.",
				"Don't watch the clock; do what it does. Keep going.", "We aim above the mark to hit the mark.",
				"You just can't beat the person who never gives up",
				"Never give up, for that is just the place and time that the tide will turn."));
	}

	/**
	 * Provide complete collection of motivational quote.
	 * 
	 * @return A collection of quote.
	 */
	public ArrayList<String> getQuotes() {
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

	/**
	 * Adding a new non-empty quote into the existing motivational quote collection.
	 * 
	 * @param newQuote
	 *            Element for adding into the the collection
	 * @return true if the specified element is successfully added.
	 */
	public boolean addQuote(String newQuote) {
		if (newQuote.length() > 0 && !quotes.contains(newQuote)) {
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
	public boolean removeQuote(String quote) {
		return quotes.remove(quote);
	}

	/**
	 * Remove all elements from the collection.
	 * 
	 * @return true if the collection is empty.
	 */
	public boolean removeAllQuote() {
		quotes = new ArrayList<>();
		return quotes.isEmpty();
	}
}
