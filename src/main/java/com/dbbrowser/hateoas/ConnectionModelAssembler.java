package com.dbbrowser.hateoas;

import com.dbbrowser.controller.connection.ConnectionController;
import com.dbbrowser.dto.ConnectionDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConnectionModelAssembler implements
        RepresentationModelAssembler<ConnectionDto, EntityModel<ConnectionDto>> {

    //relations names
    public static final String ALL_REL = "connections";

    @Override
    public EntityModel<ConnectionDto> toModel(ConnectionDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ConnectionController.class).getConnection(dto.getName())).withSelfRel(),
                linkTo(methodOn(ConnectionController.class).getAllConnections()).withRel(ALL_REL));
    }

    @Override
    public CollectionModel<EntityModel<ConnectionDto>> toCollectionModel(Iterable<? extends ConnectionDto> connections) {
        return RepresentationModelAssembler.super.toCollectionModel(connections).add(
                linkTo(methodOn(ConnectionController.class).getAllConnections()).withSelfRel());
    }

    public URI toUri(String id) {
        return linkTo(methodOn(ConnectionController.class).getConnection(id)).toUri();
    }
}
