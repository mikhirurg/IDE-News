package io.github.ijnews.exceptions;

public class IntellijNewsException extends RuntimeException {
    public IntellijNewsException(String message) {
        super(message);
    }

    public IntellijNewsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
