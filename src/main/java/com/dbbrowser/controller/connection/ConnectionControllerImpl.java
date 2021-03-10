package com.dbbrowser.controller.connection;

import com.dbbrowser.hateoas.ConnectionModelAssembler;
import com.dbbrowser.dto.ConnectionDto;
import com.dbbrowser.service.connection.ConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ConnectionControllerImpl implements ConnectionController {

    @Autowired
    private final ConnectionService connectionService;
    @Autowired
    private final ConnectionModelAssembler assembler;

    @Override
    public CollectionModel<EntityModel<ConnectionDto>> getAllConnections() {
        return assembler.toCollectionModel(connectionService.getAllConnections());
    }

    @Override
    public EntityModel<ConnectionDto> getConnection(String name) {
        return assembler.toModel(connectionService.getConnection(name));
    }

    @Override
    public ResponseEntity<EntityModel<ConnectionDto>> addConnection(ConnectionDto dto) {
        ConnectionDto added = connectionService.addConnection(dto);
        return ResponseEntity.created(assembler.toUri(added.getName())).body(assembler.toModel(added));
    }

    @Override
    public EntityModel<ConnectionDto> updateConnection(String name, ConnectionDto dto) {
        return assembler.toModel(connectionService.updateConnection(name, dto));
    }

    @Override
    public ResponseEntity<ConnectionDto> deleteConnection(String name) {
        connectionService.deleteConnection(name);
        return ResponseEntity.noContent().build();
    }
}
