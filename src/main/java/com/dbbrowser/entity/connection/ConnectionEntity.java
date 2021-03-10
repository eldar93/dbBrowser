package com.dbbrowser.entity.connection;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Connection")
@Data
public class ConnectionEntity {

    @Id
    private String name;
    private String hostname;
    private Integer port;
    @Column(name = "DATABASE_NAME")
    private String databaseName;
    private String username;
    private String password;
}
