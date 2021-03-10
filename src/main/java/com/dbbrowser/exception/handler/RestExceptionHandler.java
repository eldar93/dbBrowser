package com.dbbrowser.exception.handler;

import com.dbbrowser.dto.ExceptionDto;
import com.dbbrowser.exception.connection.ConnectionAlreadyExistsException;
import com.dbbrowser.exception.connection.ConnectionNameNotProvidedException;
import com.dbbrowser.exception.connection.ConnectionNotFoundException;
import com.dbbrowser.exception.connection.InvalidConnectionParamsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConnectionNameNotProvidedException.class)
    ResponseEntity<ExceptionDto> connectionNameNotProvidedHandler(ConnectionNameNotProvidedException ex) {
        ExceptionDto dto = new ExceptionDto(ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConnectionNotFoundException.class)
    ResponseEntity<ExceptionDto> connectionNotFoundHandler(ConnectionNotFoundException ex) {
        ExceptionDto dto = new ExceptionDto(ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConnectionAlreadyExistsException.class)
    ResponseEntity<ExceptionDto> connectionAlreadyExistsHandler(ConnectionAlreadyExistsException ex) {
        ExceptionDto dto = new ExceptionDto(ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidConnectionParamsException.class)
    ResponseEntity<ExceptionDto> invalidConnectionParamsHandler(InvalidConnectionParamsException ex) {
        ExceptionDto dto = new ExceptionDto(ex.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

}
