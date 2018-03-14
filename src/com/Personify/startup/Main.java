package com.Personify.startup;

import java.io.IOException;
import com.Personify.textView.TextInterface;

/**
 * Initialized the Personify program.
 * 
 * @author aaronsum
 * @version 2.0, 2018-03-13
 */
public class Main {

	/**
	 * Startup the system operation loop.
	 * 
	 * @param args
	 *            There are no command line expected.
	 * @throws IOException
	 *             If an input or output error occurred.
	 */
	public static void main(String[] args) throws IOException {
		TextInterface view = new TextInterface();
		view.startup();
	}
}
