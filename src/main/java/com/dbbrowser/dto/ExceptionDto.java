package com.dbbrowser.dto;

import lombok.Data;

@Data
public class ExceptionDto {

    private String errorMessage;

    public ExceptionDto(String message) {
        this.errorMessage = message;
    }
}
