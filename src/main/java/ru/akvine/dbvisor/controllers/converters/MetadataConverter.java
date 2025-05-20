package ru.akvine.dbvisor.controllers.converters;

import org.springframework.stereotype.Component;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;
import ru.akvine.dbvisor.controllers.dto.metadata.ListConstraintsRequest;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.metadata.GetConstraints;
import ru.akvine.dbvisor.utils.Asserts;

@Component
public class MetadataConverter {
    public GetConstraints convertToGetConstraints(ListConstraintsRequest request) {
        return new GetConstraints()
                .setTableName(request.getTableName())
                .setColumnName(request.getColumnName())
                .setConnectionInfo(convertToConnectionInfo(request.getConnectionInfo()));
    }

    public ConnectionInfo convertToConnectionInfo(ConnectionRequest request) {
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
