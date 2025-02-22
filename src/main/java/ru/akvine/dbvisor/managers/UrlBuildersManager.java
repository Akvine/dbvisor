package ru.akvine.dbvisor.managers;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.database.url.URLBuilder;

import java.util.Map;

public record UrlBuildersManager(Map<DatabaseType, URLBuilder> builders) {
    public URLBuilder get(DatabaseType type) {
        if (builders.containsKey(type)) {
            return builders.get(type);
        }

        String errorMessage = String.format(
                "URL builder for database type = [%s] not supported by app!",
                type
        );
        throw new UnsupportedOperationException(errorMessage);
    }
}
