package ru.akvine.dbvisor.managers;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.TypeConverterService;

import java.util.Map;

public record TypeConverterServicesManager(Map<DatabaseType, TypeConverterService> converts) {
    public TypeConverterService get(DatabaseType databaseType) {
        if (converts.containsKey(databaseType)) {
            return converts.get(databaseType);
        }

        throw new IllegalArgumentException("Type converter service for = [" + databaseType + "] is not supported by app");
    }
}
