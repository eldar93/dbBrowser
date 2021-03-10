package com.dbbrowser.dto;

import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(itemRelation = "connection", collectionRelation = "connections")
public class ConnectionDto {

    private String name;
    private String hostname;
    private Integer port;
    private String databaseName;
    private String username;
    private String password;
}
