package ru.akvine.dbvisor.controllers.meta;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.compozit.commons.dto.Response;
import ru.akvine.compozit.commons.scripts.ExecuteScriptsRequest;
import ru.akvine.dbvisor.controllers.dto.script.GenerateClearScriptRequest;

@RequestMapping(value = "/scripts")
public interface ScriptsControllerMeta {
    @PostMapping(value = "/execute")
    Response execute(@RequestBody @Valid ExecuteScriptsRequest request);

    @PostMapping(value = "/generate/clear")
    Response generateClear(@RequestBody @Valid GenerateClearScriptRequest request);
}
