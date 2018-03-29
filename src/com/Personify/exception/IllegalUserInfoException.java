package com.Personify.exception;

/**
 * Thrown to indicated that a method has been passed an illegal user information.
 */
public class IllegalUserInfoException extends Exception {
    /**
     * Construct an <code>IllegalUserInfoException</code> with detailed information message.
     *
     * @param messages the detailed information. The detail message is saved for later retrieval by the
     *                 {@link Throwable#getMessage()}method.
     */
    public IllegalUserInfoException(final String messages) {
        super(messages);
    }
}
