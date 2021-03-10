package com.dbbrowser.exception.connection;

public class ConnectionNameNotProvidedException extends RuntimeException {
    public static final String message = "Connection name is not provided";

    public ConnectionNameNotProvidedException() {
        super(message);
    }
}
