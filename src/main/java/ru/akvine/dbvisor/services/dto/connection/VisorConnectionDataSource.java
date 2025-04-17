package ru.akvine.dbvisor.services.dto.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.Getter;

@Getter
public class VisorConnectionDataSource extends HikariDataSource {
    private long lastUsedTime;

    public VisorConnectionDataSource(HikariConfig config) {
        super(config);
        this.lastUsedTime = System.currentTimeMillis();
    }


    /**
     * В данном методе используется проверка в методе {@link VisorConnectionDataSource#isAvailableToClose()}
     * что бы не случилось вызова метода {@link HikariDataSource#close()} в другом процессе который остановит текущий процесс
     */
    @Override
    public void close() {
        if (isAvailableToClose()) {
            super.close();
        } else {
            throw new IllegalStateException("Can't close connection data source because it have active connections");
        }
    }

    public boolean isAvailableToClose() {
        HikariPoolMXBean poolMXBean = super.getHikariPoolMXBean();
        int activeConnections = poolMXBean.getActiveConnections();
        int threadsAwaitingConnection = poolMXBean.getThreadsAwaitingConnection();
        return activeConnections == 0 && threadsAwaitingConnection == 0;
    }

    public void updateLastUsedTime() {
        this.lastUsedTime = System.currentTimeMillis();
    }
}
