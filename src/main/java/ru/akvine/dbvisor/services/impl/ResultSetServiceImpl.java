package ru.akvine.dbvisor.services.impl;

import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.exceptions.GetResultSetException;
import ru.akvine.dbvisor.services.ResultSetService;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ResultSetServiceImpl implements ResultSetService {

    @Override
    public ResultSet get(Connection connection, ConnectionInfo info) {
        String databaseName = info.getDatabaseName();
        String schema = info.getSchemaName();
        String[] types = new String[]{"TABLE"};

        try {
            return connection.getMetaData().getTables(databaseName, schema, null, types);
        } catch (SQLException exception) {
            String errorMessage = String.format(
                    "Error while getting resultSet. Message = [%s]", exception.getMessage()
            );
            throw new GetResultSetException(errorMessage);
        }
    }

    @Override
    public ResultSet getForColumns(Connection connection,
                                   String database,
                                   String schemaName,
                                   String tableName,
                                   DatabaseType databaseType) {
        try {
            return connection.getMetaData().getColumns(database, schemaName, tableName, null);
        } catch (SQLException exception) {
            String errorMessage = String.format(
                    "Error while getting resultSet. Message = [%s]", exception.getMessage()
            );
            throw new GetResultSetException(errorMessage);
        }
    }

    @Override
    public ResultSet getForPrimaryKeyColumns(Connection connection, String tableName) {
        try {
            return connection.getMetaData().getPrimaryKeys(null, null, tableName);
        } catch (SQLException exception) {
            String errorMessage = String.format(
                    "Error while getting resultSet. Message = [%s]", exception.getMessage()
            );
            throw new GetResultSetException(errorMessage);
        }
    }
}
