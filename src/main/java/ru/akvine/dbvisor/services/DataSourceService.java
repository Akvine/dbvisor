package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.services.dto.ConnectionInfo;

import javax.sql.DataSource;

public interface DataSourceService {
    DataSource create(ConnectionInfo info);
}
