package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.DatabaseType;

import javax.sql.DataSource;

@Data
@Accessors(chain = true)
public class GetRelatedTables {
    private DataSource dataSource;
    private DatabaseType databaseType;
    private String tableName;
    private String schema;
}
