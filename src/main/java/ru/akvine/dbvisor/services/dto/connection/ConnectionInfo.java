package ru.akvine.dbvisor.services.dto.connection;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.DatabaseType;

@Data
@Accessors(chain = true)
public class ConnectionInfo {
    private String databaseName;
    private String schemaName;
    private String host;
    private String port;
    private String username;
    private String password;
    private DatabaseType databaseType;
}
