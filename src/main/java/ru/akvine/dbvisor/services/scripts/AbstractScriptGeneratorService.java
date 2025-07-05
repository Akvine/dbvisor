package ru.akvine.dbvisor.services.scripts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.akvine.compozit.commons.enums.DeleteMode;
import ru.akvine.compozit.commons.visor.ConstraintType;
import ru.akvine.compozit.commons.visor.ScriptResultDto;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.enums.ScriptType;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.MapperService;
import ru.akvine.dbvisor.services.ScriptGeneratorService;
import ru.akvine.dbvisor.services.ScriptsConverterService;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.metadata.Constraint;
import ru.akvine.dbvisor.services.dto.metadata.Index;
import ru.akvine.dbvisor.services.dto.metadata.Trigger;
import ru.akvine.dbvisor.services.dto.script.GenerateClearScript;
import ru.akvine.dbvisor.services.dto.script.GenerateScripts;
import ru.akvine.dbvisor.services.dto.script.ScriptExecuteResult;
import ru.akvine.dbvisor.services.mappers.CommonMapper;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.utils.SqlUtils;

import javax.sql.DataSource;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractScriptGeneratorService implements ScriptGeneratorService {
    private final DataSourceService dataSourceService;
    private final JdbcTemplate jdbcTemplate;
    private final MapperService mapperService;
    private final ScriptsConverterService scriptsConverterService;

    @Override
    public ScriptExecuteResult execute(ConnectionInfo connectionInfo, Collection<String> scripts) {
        Asserts.isNotNull(connectionInfo);
        Asserts.isNotNull(scripts);

        DataSource dataSource = dataSourceService.getOrCreateDataSource(connectionInfo);
        jdbcTemplate.setDataSource(dataSource);

        Arrays.stream(String.join(";", scripts).split(";"))
                .map(s -> s.replaceAll("--.*\\n?", StringUtils.EMPTY).trim())
                .filter(StringUtils::isNotBlank)
                .map(s -> s.replace("\n", " "))
                .forEach(jdbcTemplate::execute);
        return new ScriptExecuteResult("OK", null);
    }

    @Override
    public Map<String, ScriptResultDto> generateScripts(GenerateScripts action) {
        Asserts.isNotNull(action);

        Map<String, ScriptResultDto> scripts = new HashMap<>();
        String schemaName = action.getConnectionInfo().getSchemaName();
        DataSource dataSource = dataSourceService.getOrCreateDataSource(action.getConnectionInfo());
        DatabaseType databaseType = action.getConnectionInfo().getDatabaseType();

        CommonMapper mapper = mapperService.getMapper(dataSource, databaseType);
        action.getTableNames().forEach(tableName -> {
            ScriptResultDto result = new ScriptResultDto();
            action.getConstraints().forEach(constraint -> {
                if (constraint == ConstraintType.INDEX) {
                    List<Index> uniqueIndexes = mapper.getUniqueIndexesByTable(tableName, schemaName, true);
                    result.getCreateScripts().addAll(scriptsConverterService.convertIndices(uniqueIndexes, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertIndices(uniqueIndexes, ScriptType.DROP));

                    List<Index> indexes = mapper.getNonUniqueIndexesByTable(tableName, schemaName, true);
                    result.getCreateScripts().addAll(scriptsConverterService.convertIndices(indexes, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertIndices(indexes, ScriptType.DROP));
                } else if (constraint == ConstraintType.TRIGGER) {
                    List<Trigger> triggers = mapper.getTriggersByTable(tableName, schemaName, true);

                    result.getCreateScripts().addAll(scriptsConverterService.convertTriggers(triggers, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertTriggers(triggers, ScriptType.DROP));
                } else if (constraint == ConstraintType.PRIMARY_KEY) {
                    List<Constraint> primaryKeys = mapper.getPrimaryKeyByTable(tableName, schemaName);

                    result.getCreateScripts().addAll(scriptsConverterService.convertConstraints(primaryKeys, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertConstraints(primaryKeys, ScriptType.DROP));
                } else if (constraint == ConstraintType.FOREIGN_KEY) {
                    List<Constraint> foreignKeys = mapper.getForeignKeyByTable(tableName, schemaName);

                    result.getCreateScripts().addAll(scriptsConverterService.convertConstraints(foreignKeys, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertConstraints(foreignKeys, ScriptType.DROP));
                } else if (constraint == ConstraintType.UNIQUE) {
                    List<Constraint> uniqueConstraints = mapper.getUniqueByTable(tableName, schemaName);

                    result.getCreateScripts().addAll(scriptsConverterService.convertConstraints(uniqueConstraints, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertConstraints(uniqueConstraints, ScriptType.DROP));
                } else if (constraint == ConstraintType.CHECK) {
                    List<Constraint> checkConstraints = mapper.getCheckByTable(tableName, schemaName);

                    result.getCreateScripts().addAll(scriptsConverterService.convertConstraints(checkConstraints, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertConstraints(checkConstraints, ScriptType.DROP));
                } else if (constraint == ConstraintType.DEFAULT) {
                    List<Constraint> defaultConstraints = mapper.getDefaultByTable(tableName, schemaName);

                    result.getCreateScripts().addAll(scriptsConverterService.convertConstraints(defaultConstraints, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertConstraints(defaultConstraints, ScriptType.DROP));
                } else if (constraint == ConstraintType.NOT_NULL) {
                    List<Constraint> notNullConstraints = mapper.getNotNullByTable(tableName, schemaName);

                    result.getCreateScripts().addAll(scriptsConverterService.convertConstraints(notNullConstraints, ScriptType.CREATE));
                    result.getDropScripts().addAll(scriptsConverterService.convertConstraints(notNullConstraints, ScriptType.DROP));
                } else {
                    log.debug("Unknown constraint type = [{}] for generate DROP and CREATE scripts", constraint);
                }
            });

            scripts.put(tableName, result);
        });

        return scripts;
    }

    @Override
    public String generateClearScript(GenerateClearScript action) {
        Asserts.isNotNull(action);
        Asserts.isNotNull(action.getConnectionInfo());

        ConnectionInfo connectionInfo = action.getConnectionInfo();
        String fullTableName = SqlUtils.getFullTableName(
                action.getTableName(),
                connectionInfo.getSchemaName(),
                connectionInfo.getDatabaseType()
        );

        DeleteMode deleteMode = action.getDeleteMode();
        if (deleteMode == DeleteMode.DELETE) {
            return "DELETE FROM " + fullTableName;
        }

        return "TRUNCATE TABLE " + fullTableName;
    }

    private List<String> toCreateScripts(Collection<Index> indexes) {
        return indexes.stream().map(Index::getCreateStatement).toList();
    }
}
