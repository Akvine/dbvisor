package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.enums.ScriptType;
import ru.akvine.dbvisor.services.dto.metadata.Constraint;
import ru.akvine.dbvisor.services.dto.metadata.Index;
import ru.akvine.dbvisor.services.dto.metadata.Trigger;

import java.util.Collection;
import java.util.List;

public interface ScriptsConverterService {
    List<String> convertIndices(Collection<Index> indices, ScriptType scriptType);

    List<String> convertTriggers(Collection<Trigger> triggers, ScriptType scriptType);

    List<String> convertConstraints(Collection<Constraint> constraints, ScriptType scriptType);

}
