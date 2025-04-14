package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.ConvertAction;

public interface TypeConverterService {
    Object convert(ConvertAction action);

    DatabaseType getByDatabaseType();
}
