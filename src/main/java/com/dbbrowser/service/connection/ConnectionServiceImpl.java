package com.dbbrowser.service.connection;

import com.dbbrowser.dto.ConnectionDto;
import com.dbbrowser.entity.connection.ConnectionEntity;
import com.dbbrowser.exception.connection.ConnectionAlreadyExistsException;
import com.dbbrowser.exception.connection.ConnectionNotFoundException;
import com.dbbrowser.repository.connection.ConnectionRepository;
import com.dbbrowser.validation.ConnectionValidationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Transactional
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private final ConnectionRepository repository;
    @Autowired
    private final ConnectionValidationService validationService;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public List<ConnectionDto> getAllConnections() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(
                c -> modelMapper.map(c, ConnectionDto.class)).collect(Collectors.toList());
    }

    @Override
    public ConnectionDto getConnection(String name) {
        ConnectionEntity conn = repository.findById(name).orElseThrow(
                () -> new ConnectionNotFoundException(name));
        ConnectionDto dto = modelMapper.map(conn, ConnectionDto.class);
        return dto;
    }

    @Override
    public ConnectionDto addConnection(ConnectionDto dto) {
        validationService.validateAddConnection(dto);
        if (repository.existsById(dto.getName())) {
            throw new ConnectionAlreadyExistsException(dto.getName());
        }
        ConnectionEntity conn = modelMapper.map(dto, ConnectionEntity.class);
        repository.save(conn);
        return modelMapper.map(conn, ConnectionDto.class);
    }

    @Override
    public ConnectionDto updateConnection(String name, ConnectionDto dto) {
        //shadowing id as it is illegal to change it (assuming it is different in the body)
        dto.setName(name);
        ConnectionEntity conn = repository.findById(name).orElseThrow(
                () -> new ConnectionNotFoundException(name));
        modelMapper.map(dto, conn);
        return modelMapper.map(conn, ConnectionDto.class);
    }

    @Override
    public void deleteConnection(String name) {
        repository.deleteById(name);
    }

}
