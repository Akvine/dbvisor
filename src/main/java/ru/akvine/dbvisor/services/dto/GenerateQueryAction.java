package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.DatabaseType;

import java.util.Collection;

@Data
@Accessors(chain = true)
public class GenerateQueryAction {
    private String tableName;
    private Collection<String> columnsNames;
    private DatabaseType databaseType;
}
