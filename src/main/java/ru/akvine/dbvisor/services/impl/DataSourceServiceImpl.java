package ru.akvine.dbvisor.services.impl;

import com.zaxxer.hikari.HikariConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.managers.UrlBuildersManager;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.connection.ConnectionPoolStore;
import ru.akvine.dbvisor.services.dto.connection.VisorConnectionDataSource;
import ru.akvine.dbvisor.utils.Asserts;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataSourceServiceImpl implements DataSourceService {
    private final UrlBuildersManager manager;

    @Value("${datasource.connection.pool.max.size}")
    private int connectionPoolMaxSize;
    @Value("${dataSource.lifeTime.milliseconds}")
    private long lifeTime;
    @Value("${dataSource.checkTime.milliseconds}")
    private int checkTime;

    private ConnectionPoolStore connectionPoolStore;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        connectionPoolStore = new ConnectionPoolStore(lifeTime, checkTime);
        Thread connectionPoolListenerThread = new Thread(connectionPoolStore);
        connectionPoolListenerThread.setDaemon(true);
        connectionPoolListenerThread.setPriority(Thread.MIN_PRIORITY);
        connectionPoolListenerThread.setName("connection-pools-listener");
        //Set the context class loader to null in order to avoid keeping a strong reference to an application classloader.
        connectionPoolListenerThread.setContextClassLoader(null);
        connectionPoolListenerThread.start();
    }

    @Override
    public VisorConnectionDataSource createHikariDataSource(ConnectionInfo info) {
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

        return new VisorConnectionDataSource(config);
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

    @Override
    public synchronized VisorConnectionDataSource getOrCreateDataSource(ConnectionInfo connectionInfo) {
        String key = generateKey(connectionInfo);
        if (connectionPoolStore.contains(key)) {
            VisorConnectionDataSource dataSource = connectionPoolStore.get(key);
            dataSource.updateLastUsedTime();
            connectionPoolStore.put(key, dataSource);
        } else {
            VisorConnectionDataSource dataSource = createHikariDataSource(connectionInfo);
            connectionPoolStore.put(key, dataSource);
        }

        log.debug("DataSource used in thread {}!", Thread.currentThread().getName());
        return connectionPoolStore.get(key);
    }

    private String generateKey(ConnectionInfo info) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(info.getDatabaseName())
                .append(info.getHost())
                .append(info.getPort())
                .append(info.getDatabaseType())
                .append(info.getPassword())
                .append(info.getUsername());
        return sb.toString();
    }
}
