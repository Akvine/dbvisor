package ru.akvine.dbvisor.services.dto.metadata;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.ConstraintType;

import java.util.List;

@Data
@Accessors(chain = true)
public class ColumnMetadata {
    private String database;
    private String schemaName;
    private String columnName;
    private String tableName;
    private String dataType;
    private int orderIndex;
    private int size;
    private boolean generatedAlways;
    private boolean primaryKey;
    private List<ConstraintType> constraints;
}
