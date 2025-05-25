package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.script.GenerateClearScript;
import ru.akvine.dbvisor.services.dto.script.ScriptExecuteResult;

import java.util.Collection;

public interface ScriptGeneratorService {
    String generateClearScript(GenerateClearScript action);

    ScriptExecuteResult execute(ConnectionInfo connectionInfo, Collection<String> scripts);

    DatabaseType getDatabaseType();
}
