package ru.akvine.dbvisor.services;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;

import javax.sql.DataSource;

public interface DataSourceService {
    DataSource createHikariDataSource(ConnectionInfo info);

    SimpleDriverDataSource createSimpleDriverDataSource(ConnectionInfo connectionInfo);
}
