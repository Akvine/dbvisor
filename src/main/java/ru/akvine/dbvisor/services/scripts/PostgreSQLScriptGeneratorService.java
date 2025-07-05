package ru.akvine.dbvisor.services.scripts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.MapperService;
import ru.akvine.dbvisor.services.ScriptsConverterService;

@Service
public class PostgreSQLScriptGeneratorService extends AbstractScriptGeneratorService {

    @Autowired
    public PostgreSQLScriptGeneratorService(DataSourceService dataSourceService,
                                            JdbcTemplate jdbcTemplate,
                                            MapperService mapperService,
                                            ScriptsConverterService scriptsConverterService) {
        super(dataSourceService, jdbcTemplate, mapperService, scriptsConverterService);
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.POSTGRESQL;
    }
}
