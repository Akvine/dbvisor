package ru.akvine.dbvisor.controllers.converters;

import org.springframework.stereotype.Component;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionDto;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionResponse;
import ru.akvine.dbvisor.controllers.dto.connection.CreateConnectionRequest;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.domain.Connection;
import ru.akvine.dbvisor.services.dto.connection.CreateConnection;
import ru.akvine.dbvisor.utils.Asserts;

import java.util.List;

@Component
public class ConnectionConverter {
    public CreateConnection convertToCreateConnection(CreateConnectionRequest request) {
        Asserts.isNotNull(request);
        return new CreateConnection()
                .setConnectionName(request.getConnectionName())
                .setDatabaseName(request.getDatabaseName())
                .setHost(request.getHost())
                .setPort(request.getPort())
                .setSchema(request.getSchema())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setDatabaseType(DatabaseType.from(request.getDatabaseType()));
    }

    public ConnectionResponse convertToConnectionResponse(List<Connection> connections) {
        return new ConnectionResponse().setConnections(connections.stream().map(this::buildConnectionDto).toList());
    }

    private ConnectionDto buildConnectionDto(Connection connection) {
        return new ConnectionDto()
                .setConnectionName(connection.getConnectionName())
                .setDatabaseName(connection.getDatabaseName())
                .setSchema(connection.getSchema())
                .setHost(connection.getHost())
                .setPort(connection.getPort())
                .setDatabaseType(connection.getDatabaseType().toString());
    }
}
