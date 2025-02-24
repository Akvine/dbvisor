package ru.akvine.dbvisor.services.dto.connection;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.DatabaseType;

@Data
@Accessors(chain = true)
public class CreateConnection {
    private DatabaseType databaseType;
    private String databaseName;
    private String connectionName;
    @ToString.Exclude
    private String username;
    @ToString.Exclude
    private String password;
    private String host;
    private String port;
    private String schema;
}
