package ru.akvine.dbvisor.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import ru.akvine.dbvisor.enums.DatabaseType;

@UtilityClass
public class SqlUtils {
    public String getFullTableName(String tableName, String schemaName, DatabaseType dbType) {
        switch (dbType) {
            case POSTGRESQL -> {
                return (schemaName != null ? ('"' + schemaName + "\".") : StringUtils.EMPTY) + '"' + tableName + '"';
            }
            default -> throw new IllegalArgumentException("Unsupported data base type = [" + dbType + "] is not supported");
        }
    }
}
