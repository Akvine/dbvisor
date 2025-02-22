package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.mappers.CommonMapper;

import javax.sql.DataSource;

public interface MapperService {
    <T extends CommonMapper> T getMapper(DataSource dataSource, DatabaseType databaseType);
}
