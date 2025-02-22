package ru.akvine.dbvisor.services.database.url;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;

public interface URLBuilder {
    String build(ConnectionInfo info);

    DatabaseType getType();
}
