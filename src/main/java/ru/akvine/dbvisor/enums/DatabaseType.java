package ru.akvine.dbvisor.enums;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.akvine.dbvisor.services.mappers.CommonMapper;
import ru.akvine.dbvisor.services.mappers.PostgreSQLMapper;

@Getter
@AllArgsConstructor
public enum DatabaseType {
    POSTGRESQL("org.postgresql.Driver", PostgreSQLMapper.class);

    private final String driver;
    private final Class<? extends CommonMapper> mapper;

    public static DatabaseType from(String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("DatabaseType value is blank!");
        }

        switch (value.toLowerCase()) {
            case "postgresql" -> {
                return POSTGRESQL;
            }
            default -> throw new UnsupportedOperationException("Database with type = [" + value + "] is not supported by app!");
        }
    }
}
