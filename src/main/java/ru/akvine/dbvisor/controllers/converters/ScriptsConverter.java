package ru.akvine.dbvisor.controllers.converters;

import org.springframework.stereotype.Component;
import ru.akvine.compozit.commons.GenerateClearScriptResponse;
import ru.akvine.compozit.commons.enums.DeleteMode;
import ru.akvine.dbvisor.controllers.dto.script.GenerateClearScriptRequest;
import ru.akvine.dbvisor.services.dto.script.GenerateClearScript;
import ru.akvine.dbvisor.utils.Asserts;

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
}
