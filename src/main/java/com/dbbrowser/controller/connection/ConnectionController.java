package com.dbbrowser.controller.connection;

import com.dbbrowser.dto.ConnectionDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/connections")
public interface ConnectionController {

    String ID_PATH = "/{name}";

    @GetMapping
    CollectionModel<EntityModel<ConnectionDto>> getAllConnections();
    @GetMapping(path = ID_PATH)
    EntityModel<ConnectionDto> getConnection(@PathVariable String name);
    @PostMapping
    ResponseEntity<EntityModel<ConnectionDto>> addConnection(@RequestBody ConnectionDto dto);
    @PatchMapping(path = ID_PATH)
    EntityModel<ConnectionDto> updateConnection(@PathVariable String name, @RequestBody ConnectionDto dto);
    @DeleteMapping(path = ID_PATH)
    ResponseEntity<ConnectionDto> deleteConnection(@PathVariable String name);

}
