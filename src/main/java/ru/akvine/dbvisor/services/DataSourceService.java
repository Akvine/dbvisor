package ru.akvine.dbvisor.services;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.connection.VisorConnectionDataSource;

public interface DataSourceService {
    VisorConnectionDataSource createHikariDataSource(ConnectionInfo info);

    SimpleDriverDataSource createSimpleDriverDataSource(ConnectionInfo connectionInfo);

    VisorConnectionDataSource getOrCreateDataSource(ConnectionInfo connectionInfo);
}
