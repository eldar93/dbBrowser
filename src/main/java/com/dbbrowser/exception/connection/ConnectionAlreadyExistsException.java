package com.dbbrowser.exception.connection;

public class ConnectionAlreadyExistsException extends RuntimeException {

    public static final String MESSAGE_PATTERN = "Connection with the name '%s' already exists";

    public ConnectionAlreadyExistsException(String name) {
        super(String.format(MESSAGE_PATTERN, name));
    }
}
