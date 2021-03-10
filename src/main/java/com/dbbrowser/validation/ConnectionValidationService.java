package com.dbbrowser.validation;

import com.dbbrowser.dto.ConnectionDto;
import com.dbbrowser.exception.connection.ConnectionNameNotProvidedException;
import com.dbbrowser.exception.connection.InvalidConnectionParamsException;
import org.springframework.stereotype.Component;

@Component
public class ConnectionValidationService {

    public static final String INVALID_PORT = "Invalid port range. Port number should be between 0 and 65535";
    private static final Integer MIN_PORT = 0;
    private static final Integer MAX_PORT = 65535;

    public void validatePort(Integer port) {
        if (port != null && (port < MIN_PORT || port > MAX_PORT)) {
            throw new InvalidConnectionParamsException(INVALID_PORT);
        }
    }

    public void validateAddConnection(ConnectionDto dto) {
        if (dto.getName() == null) {
            throw new ConnectionNameNotProvidedException();
        }
    }
}
