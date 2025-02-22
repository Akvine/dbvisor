package ru.akvine.dbvisor.enums;

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
}
