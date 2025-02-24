package ru.akvine.dbvisor.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.repositories.ConnectionRepository;
import ru.akvine.dbvisor.repositories.entities.ConnectionEntity;
import ru.akvine.dbvisor.services.ConnectionService;
import ru.akvine.dbvisor.services.domain.Connection;
import ru.akvine.dbvisor.services.dto.connection.CreateConnection;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.utils.UUIDGenerator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {
    private final ConnectionRepository connectionRepository;

    @Override
    public List<Connection> list() {
        return connectionRepository.findAll().stream()
                .map(Connection::new)
                .toList();
    }

    @Override
    public Connection create(CreateConnection connection) {
        Asserts.isNotNull(connection);
        ConnectionEntity connectionEntity = (ConnectionEntity) new ConnectionEntity()
                .setConnectionName(connection.getConnectionName())
                .setHost(connection.getHost())
                .setPort(connection.getPort())
                .setSchema(connection.getSchema())
                .setPassword(connection.getPassword())
                .setUsername(connection.getUsername())
                .setDatabaseName(connection.getDatabaseName())
                .setDatabaseType(connection.getDatabaseType())
                .setUuid(UUIDGenerator.uuidWithoutDashes());
        return new Connection(connectionRepository.save(connectionEntity));
    }


}
