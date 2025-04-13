package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.enums.DatabaseType;

public interface ConverterService {
    Object convert(String value);

    DatabaseType getByDatabaseType();
}
