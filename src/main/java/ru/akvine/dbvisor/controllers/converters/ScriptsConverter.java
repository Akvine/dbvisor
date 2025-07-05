package ru.akvine.dbvisor.controllers.converters;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.akvine.compozit.commons.GenerateClearScriptResponse;
import ru.akvine.compozit.commons.enums.DeleteMode;
import ru.akvine.compozit.commons.visor.ConstraintType;
import ru.akvine.compozit.commons.visor.GenerateScriptsRequest;
import ru.akvine.compozit.commons.visor.GenerateScriptsResponse;
import ru.akvine.compozit.commons.visor.ScriptResultDto;
import ru.akvine.dbvisor.controllers.dto.script.GenerateClearScriptRequest;
import ru.akvine.dbvisor.services.dto.script.GenerateClearScript;
import ru.akvine.dbvisor.services.dto.script.GenerateScripts;
import ru.akvine.dbvisor.utils.Asserts;

import java.util.List;
import java.util.Map;

@Component
public class ScriptsConverter extends ConnectionConverter {
    public GenerateClearScript convertToGenerateClearScript(GenerateClearScriptRequest request) {
        Asserts.isNotNull(request);
        return new GenerateClearScript()
                .setConnectionInfo(convert(request.getConnection()))
                .setTableName(request.getTableName())
                .setDeleteMode(DeleteMode.from(request.getDeleteMode()));
    }

    public GenerateClearScriptResponse convertToGenerateClearScriptResponse(String result) {
        return new GenerateClearScriptResponse().setResult(result);
    }

    public GenerateScripts convertToGenerateScripts(GenerateScriptsRequest request) {
        return new GenerateScripts()
                .setTableNames(request.getTableNames())
                .setConstraints(CollectionUtils.isNotEmpty(request.getConstraints()) ?
                        request.getConstraints().stream().map(ConstraintType::fromName).toList() : List.of())
                .setConnectionInfo(convert(request.getConnection()));
    }

    public GenerateScriptsResponse convertToGenerateScriptsResponse(Map<String, ScriptResultDto> scripts) {
        Asserts.isNotNull(scripts);
        return new GenerateScriptsResponse()
                .setTableNameWithScripts(scripts);
    }
}
