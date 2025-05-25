package ru.akvine.dbvisor.controllers.converters;

import ru.akvine.compozit.commons.ConnectionRequest;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.utils.Asserts;

public abstract class ConnectionConverter {
    public ConnectionInfo convert(ConnectionRequest request) {
        Asserts.isNotNull(request);
        return new ConnectionInfo()
                .setDatabaseName(request.getDatabaseName())
                .setHost(request.getHost())
                .setPort(request.getPort())
                .setSchemaName(request.getSchema())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setDatabaseType(DatabaseType.from(request.getDatabaseType()));
    }
}
