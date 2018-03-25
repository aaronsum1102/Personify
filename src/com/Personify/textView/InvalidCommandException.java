package com.Personify.textView;

class InvalidCommandException extends Exception {
    InvalidCommandException(final int number) {
        super(String.format("Warning: Command given of %d is not a valid selection.", number));
    }
}
