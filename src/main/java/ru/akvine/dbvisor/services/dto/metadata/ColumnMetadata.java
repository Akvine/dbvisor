package ru.akvine.dbvisor.services.dto.metadata;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.compozit.commons.visor.ConstraintType;

import java.util.List;

@Data
@Accessors(chain = true)
public class ColumnMetadata {
    private String database;
    private String schemaName;
    private String columnName;
    private String tableName;
    private String dataType;
    @Nullable
    private String targetColumnNameForForeignKey;
    private int orderIndex;
    private int size;
    private boolean generatedAlways;
    private boolean primaryKey;
    private List<ConstraintType> constraints;
}
