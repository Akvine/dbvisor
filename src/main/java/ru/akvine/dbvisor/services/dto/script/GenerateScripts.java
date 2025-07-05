package ru.akvine.dbvisor.services.dto.script;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.compozit.commons.visor.ConstraintType;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;

import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class GenerateScripts {
    private Collection<String> tableNames = List.of();
    private ConnectionInfo connectionInfo;
    private List<ConstraintType> constraints;
}
