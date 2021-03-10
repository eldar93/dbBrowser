package com.dbbrowser.service.connection;

import com.dbbrowser.dto.ConnectionDto;

import java.util.List;

public interface ConnectionService {

    List<ConnectionDto> getAllConnections();
    ConnectionDto getConnection(String name);
    ConnectionDto addConnection(ConnectionDto dto);
    ConnectionDto updateConnection(String name, ConnectionDto dto);
    void deleteConnection(String name);
}
