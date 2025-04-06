package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.DatabaseType;

import javax.sql.DataSource;

@Data
@Accessors(chain = true)
public class GetColumnsAction {
    private DataSource dataSource;
    private String database;
    private String schemaName;
    private String tableName;
    private DatabaseType type;
}
