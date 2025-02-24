package ru.akvine.dbvisor.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.akvine.dbvisor.exceptions.CheckConnectionException;
import ru.akvine.dbvisor.exceptions.ConnectionNotFoundException;
import ru.akvine.dbvisor.repositories.ConnectionRepository;
import ru.akvine.dbvisor.repositories.entities.ConnectionEntity;
import ru.akvine.dbvisor.services.ConnectionService;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.domain.Connection;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.connection.CreateConnection;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.utils.UUIDGenerator;

import javax.sql.DataSource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {
    private final ConnectionRepository connectionRepository;

    private final DataSourceService dataSourceService;

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

    @Override
    public void checkConnection(String connectionName) {
        Asserts.isNotNull(connectionName);

        ConnectionEntity connection = verifyExistsByConnectionName(connectionName);
        ConnectionInfo connectionInfo = new ConnectionInfo()
                .setDatabaseName(connection.getDatabaseName())
                .setSchemaName(connection.getSchema())
                .setDatabaseType(connection.getDatabaseType())
                .setHost(connection.getHost())
                .setPort(connection.getPort())
                .setUsername(connection.getUsername())
                .setPassword(connection.getPassword());

        DataSource source = dataSourceService.createSimpleDriverDataSource(connectionInfo);

        // TODO : добавить в domain-dto постфикс Model, чтобы не было совпадений с классами из JDK
        try(java.sql.Connection datasourceConnection = source.getConnection()) {
            Assert.isInstanceOf(java.sql.Connection.class, datasourceConnection);
        } catch (Exception exception) {
            String errorMessage = String.format(
                    "Error during check database connection for %s. Db unavailable. Reason = [%s]",
                    connection.getConnectionName(),
                    exception.getMessage()
            );
            throw new CheckConnectionException(errorMessage);
        }
    }

    @Override
    public Connection getByConnectionName(String connectionName) {
        return new Connection(verifyExistsByConnectionName(connectionName));
    }

    @Override
    public ConnectionEntity verifyExistsByConnectionName(String connectionName) {
        Asserts.isNotNull(connectionName);
        return connectionRepository
                .findByConnectionName(connectionName)
                .orElseThrow(() -> new ConnectionNotFoundException("Connection with name = [" + connectionName + "] not found!"));
    }


}
