package ru.akvine.dbvisor.services.scripts;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.akvine.compozit.commons.enums.DeleteMode;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.ScriptGeneratorService;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.script.GenerateClearScript;
import ru.akvine.dbvisor.services.dto.script.ScriptExecuteResult;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.utils.SqlUtils;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public abstract class AbstractScriptGeneratorService implements ScriptGeneratorService {
    private final DataSourceService dataSourceService;
    private final JdbcTemplate jdbcTemplate;

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
}
