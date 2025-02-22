package ru.akvine.dbvisor.controllers.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetTablesRequest {
    private String databaseName;
    private String host;
    private String port;
    private String schema;
    private String username;
    private String password;
}
