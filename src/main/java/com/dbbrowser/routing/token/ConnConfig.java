package com.dbbrowser.routing.token;

import lombok.Data;

@Data
public final class ConnConfig {

    private final String id;
    private final String url;
    private final String user;
    private final String password;

}