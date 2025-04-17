package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetService {
    ResultSet get(Connection connection, ConnectionInfo info) throws SQLException;

    ResultSet getForColumns(Connection connection,
                            String database,
                            String schemaName,
                            String tableName,
                            DatabaseType databaseType);

    ResultSet getForPrimaryKeyColumns(Connection connection, String tableName);
}
