package ru.akvine.dbvisor.services.scripts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.DataSourceService;

@Service
public class PostgreSQLScriptGeneratorService extends AbstractScriptGeneratorService {

    @Autowired
    public PostgreSQLScriptGeneratorService(DataSourceService dataSourceService,
                                            JdbcTemplate jdbcTemplate) {
        super(dataSourceService, jdbcTemplate);
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.POSTGRESQL;
    }
}
