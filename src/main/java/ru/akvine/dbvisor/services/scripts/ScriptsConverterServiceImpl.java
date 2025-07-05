package ru.akvine.dbvisor.services.scripts;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.ScriptType;
import ru.akvine.dbvisor.services.ScriptsConverterService;
import ru.akvine.dbvisor.services.dto.metadata.Constraint;
import ru.akvine.dbvisor.services.dto.metadata.Index;
import ru.akvine.dbvisor.services.dto.metadata.Trigger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScriptsConverterServiceImpl implements ScriptsConverterService {

    @Override
    public List<String> convertIndices(Collection<Index> indices, ScriptType scriptType) {
        if (scriptType == ScriptType.DROP) {
            return indices.stream()
                    .map(index -> {
                        if (StringUtils.isNotBlank(index.getDisableStatement())) {
                            return index.getDisableStatement();
                        } else {
                            return index.getDropStatement();
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } else {
            return indices.stream()
                    .map(index -> {
                        if (StringUtils.isNotBlank(index.getEnableStatement())) {
                            return index.getEnableStatement();
                        } else {
                            return index.getCreateStatement();
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }
    }

    @Override
    public List<String> convertTriggers(Collection<Trigger> triggers, ScriptType scriptType) {
        if (scriptType == ScriptType.DROP) {
            return triggers.stream()
                    .map(index -> {
                        if (StringUtils.isNotBlank(index.getDisableStatement())) {
                            return index.getDisableStatement();
                        } else {
                            return index.getDropStatement();
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } else {
            return triggers.stream()
                    .map(index -> {
                        if (StringUtils.isNotBlank(index.getEnableStatement())) {
                            return index.getEnableStatement();
                        } else {
                            return index.getCreateStatement();
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }
    }

    @Override
    public List<String> convertConstraints(Collection<Constraint> constraints, ScriptType scriptType) {
        if (scriptType == ScriptType.DROP) {
            return constraints.stream()
                    .map(Constraint::getDropStatement)
                    .filter(Objects::nonNull)
                    .toList();
        } else {
            return constraints.stream()
                    .map(Constraint::getCreateStatement)
                    .filter(Objects::nonNull)
                    .toList();
        }
    }

}
