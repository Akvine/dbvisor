package ru.akvine.dbvisor.services.dto.script;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ScriptExecuteResult {
    private String status;
    private String message;
}
