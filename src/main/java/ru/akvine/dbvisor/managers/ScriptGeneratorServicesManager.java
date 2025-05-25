package ru.akvine.dbvisor.managers;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.ScriptGeneratorService;

import java.util.Map;

public record ScriptGeneratorServicesManager(Map<DatabaseType, ScriptGeneratorService> generators) {
    public ScriptGeneratorService get(DatabaseType databaseType) {
        if (generators.containsKey(databaseType)) {
            return generators.get(databaseType);
        }

        throw new IllegalArgumentException("Script generator service for = [" + databaseType + "] is not supported by app");
    }
}
