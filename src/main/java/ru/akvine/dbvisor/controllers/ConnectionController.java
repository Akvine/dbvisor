package ru.akvine.dbvisor.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.dbvisor.controllers.converters.ConnectionConverter;
import ru.akvine.dbvisor.controllers.dto.common.Response;
import ru.akvine.dbvisor.controllers.dto.common.SuccessfulResponse;
import ru.akvine.dbvisor.controllers.dto.connection.CreateConnectionRequest;
import ru.akvine.dbvisor.controllers.meta.ConnectionControllerMeta;
import ru.akvine.dbvisor.controllers.validators.ConnectionValidator;
import ru.akvine.dbvisor.services.ConnectionService;
import ru.akvine.dbvisor.services.domain.Connection;
import ru.akvine.dbvisor.services.dto.connection.CreateConnection;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConnectionController implements ConnectionControllerMeta {
    private final ConnectionValidator connectionValidator;
    private final ConnectionConverter connectionConverter;
    private final ConnectionService connectionService;

    @Override
    public Response list() {
        List<Connection> connections = connectionService.list();
        return connectionConverter.convertToConnectionResponse(connections);
    }

    @Override
    public Response create(@RequestBody @Valid CreateConnectionRequest request) {
        connectionValidator.verifyCreateConnectionRequest(request);
        CreateConnection createConnection = connectionConverter.convertToCreateConnection(request);
        Connection createdConnection = connectionService.create(createConnection);
        return connectionConverter.convertToConnectionResponse(List.of(createdConnection));
    }

    @Override
    public Response check(String connectionName) {
        connectionService.checkConnection(connectionName);
        return new SuccessfulResponse();
    }
}
