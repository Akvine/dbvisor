package ru.akvine.dbvisor.services.dto.script;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.compozit.commons.enums.DeleteMode;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;

@Data
@Accessors(chain = true)
public class GenerateClearScript {
    private ConnectionInfo connectionInfo;
    private String tableName;
    private DeleteMode deleteMode;
}
