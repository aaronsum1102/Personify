package com.Personify.exception;

/**
 * Thrown to indicate an invalid command was given by user.
 */
public class InvalidCommandException extends Exception {
    /**
     * Construct <code>InvalidCommandException</code> with detailed information and the command given by user.
     *
     * @param number the detailed information. The detail message is saved for later retrieval by the
     *               {@link Throwable#getMessage()}method.
     */
    public InvalidCommandException(final int number) {
        super(String.format("Warning: Command given of %d is not a valid selection.", number));
    }
}
