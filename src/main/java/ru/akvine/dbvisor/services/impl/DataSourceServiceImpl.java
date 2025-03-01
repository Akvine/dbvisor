package ru.akvine.dbvisor.services.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.managers.UrlBuildersManager;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;
import ru.akvine.dbvisor.utils.Asserts;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class DataSourceServiceImpl implements DataSourceService {
    private final UrlBuildersManager manager;

    @Value("${datasource.connection.pool.max.size}")
    private int connectionPoolMaxSize;

    @Override
    public DataSource createHikariDataSource(ConnectionInfo info) {
        Asserts.isNotNull(info);

        DatabaseType type = info.getDatabaseType();
        String driverClassName = type.getDriver();

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(manager.get(type).build(info));
        config.setUsername(info.getUsername());
        config.setPassword(info.getPassword());
        config.setMaximumPoolSize(connectionPoolMaxSize);
        config.setPoolName("springHikariCP_" + driverClassName);

        return new HikariDataSource(config);
    }

    @Override
    public SimpleDriverDataSource createSimpleDriverDataSource(ConnectionInfo connectionInfo) {
        Asserts.isNotNull(connectionInfo);

        DatabaseType databaseType = connectionInfo.getDatabaseType();
        String driverClassName = databaseType.getDriver();
        String url = manager.get(databaseType).build(connectionInfo);

        // TODO : вынести username и password в отдельную dto: Credentials
        String username = connectionInfo.getUsername();
        String password = connectionInfo.getPassword();

        return DataSourceBuilder
                .create()
                .type(SimpleDriverDataSource.class)
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }
}
