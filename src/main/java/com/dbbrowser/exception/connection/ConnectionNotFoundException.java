package com.dbbrowser.exception.connection;

public class ConnectionNotFoundException extends RuntimeException {
    public static final String MESSAGE_PATTERN = "Connection with the name '%s' not found";

    public ConnectionNotFoundException(String name) {
        super(String.format(MESSAGE_PATTERN, name));
    }

}
