package ru.akvine.dbvisor.services.dto.metadata;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TableMetadata {
    private String tableName;
    private String schema;
    private String database;
}
