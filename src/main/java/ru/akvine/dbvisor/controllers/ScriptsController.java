package ru.akvine.dbvisor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.compozit.commons.dto.Response;
import ru.akvine.compozit.commons.dto.SuccessfulResponse;
import ru.akvine.compozit.commons.scripts.ExecuteScriptsRequest;
import ru.akvine.compozit.commons.visor.GenerateScriptsRequest;
import ru.akvine.compozit.commons.visor.ScriptResultDto;
import ru.akvine.dbvisor.controllers.converters.ScriptsConverter;
import ru.akvine.dbvisor.controllers.dto.script.GenerateClearScriptRequest;
import ru.akvine.dbvisor.controllers.meta.ScriptsControllerMeta;
import ru.akvine.dbvisor.managers.ScriptGeneratorServicesManager;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.script.GenerateClearScript;
import ru.akvine.dbvisor.services.dto.script.GenerateScripts;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ScriptsController implements ScriptsControllerMeta {
    private final ScriptsConverter scriptsConverter;
    private final ScriptGeneratorServicesManager scriptGeneratorServicesManager;

    @Override
    public Response execute(ExecuteScriptsRequest request) {
        ConnectionInfo connectionInfo = scriptsConverter.convert(request.getConnection());
        scriptGeneratorServicesManager.get(connectionInfo.getDatabaseType()).execute(
                connectionInfo,
                request.getScripts());
        return new SuccessfulResponse();
    }

    @Override
    public Response generateClear(GenerateClearScriptRequest request) {
        GenerateClearScript action = scriptsConverter.convertToGenerateClearScript(request);
        String result = scriptGeneratorServicesManager
                .get(action.getConnectionInfo().getDatabaseType())
                .generateClearScript(action);
        return scriptsConverter.convertToGenerateClearScriptResponse(result);
    }

    @Override
    public Response generate(GenerateScriptsRequest request) {
        GenerateScripts action = scriptsConverter.convertToGenerateScripts(request);
        Map<String, ScriptResultDto> scripts = scriptGeneratorServicesManager
                .get(action.getConnectionInfo().getDatabaseType()).generateScripts(action);
        return scriptsConverter.convertToGenerateScriptsResponse(scripts);
    }
}
