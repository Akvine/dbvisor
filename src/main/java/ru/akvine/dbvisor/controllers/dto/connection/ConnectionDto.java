package ru.akvine.dbvisor.controllers.dto.connection;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConnectionDto {
    private String connectionName;

    private String databaseName;

    private String databaseType;

    private String host;

    private String port;

    private String schema;

    private String username;

    private String password;

}
