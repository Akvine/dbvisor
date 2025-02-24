package ru.akvine.dbvisor.services.domain;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.repositories.entities.ConnectionEntity;

@Data
@Accessors(chain = true)
public class Connection extends Model<Long> {
    private String connectionName;
    @Nullable
    private String databaseName;
    @Nullable
    private String schema;
    private String host;
    private String port;
    @ToString.Exclude
    private String username;
    @ToString.Exclude
    private String password;
    private DatabaseType databaseType;

    public Connection(ConnectionEntity connection) {
        super(connection);

        this.connectionName = connection.getConnectionName();
        this.databaseName = connection.getDatabaseName();
        this.host = connection.getHost();
        this.port = connection.getPort();
        this.schema = connection.getSchema();
        this.username = connection.getUsername();
        this.password = connection.getPassword();
        this.databaseType = connection.getDatabaseType();
    }
}
