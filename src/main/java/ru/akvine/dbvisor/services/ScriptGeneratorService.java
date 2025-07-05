package ru.akvine.dbvisor.services;

import ru.akvine.compozit.commons.visor.ScriptResultDto;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.script.GenerateClearScript;
import ru.akvine.dbvisor.services.dto.script.GenerateScripts;
import ru.akvine.dbvisor.services.dto.script.ScriptExecuteResult;

import java.util.Collection;
import java.util.Map;

public interface ScriptGeneratorService {
    String generateClearScript(GenerateClearScript action);

    Map<String, ScriptResultDto> generateScripts(GenerateScripts action);

    ScriptExecuteResult execute(ConnectionInfo connectionInfo, Collection<String> scripts);

    DatabaseType getDatabaseType();
}
