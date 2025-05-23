package ru.akvine.dbvisor.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.exceptions.CheckConnectionException;
import ru.akvine.dbvisor.exceptions.GetMetadataException;
import ru.akvine.dbvisor.exceptions.InsertValuesException;
import ru.akvine.dbvisor.managers.TypeConverterServicesManager;
import ru.akvine.dbvisor.services.*;
import ru.akvine.dbvisor.services.dto.*;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.connection.VisorConnectionDataSource;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetaInfo;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetadata;
import ru.akvine.dbvisor.services.dto.metadata.RelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.utils.CryptoUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    // TODO: слишком много зависимостей
    private final ResultSetService resultSetService;
    private final MapperService mapperService;
    private final DataSourceService dataSourceService;
    private final ParseService parseService;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final QueryService queryService;
    private final TypeConverterServicesManager typeConverterServicesManager;

    @Value("${insert.values.batch.size}")
    private int maxBatchSize;

    @Override
    public List<TableMetadata> getTables(DataSource source, ConnectionInfo info) {

        List<TableMetadata> tableMetadata = new ArrayList<>();
        try (Connection connection = source.getConnection();
             ResultSet resultSet = resultSetService.get(connection, info)) {

            while (resultSet.next()) {
                TableMetadata metadata = new TableMetadata();
                metadata.setDatabase(resultSet.getString("TABLE_CAT"));
                metadata.setSchema(resultSet.getString("TABLE_SCHEM"));
                metadata.setTableName(resultSet.getString("TABLE_NAME"));
                tableMetadata.add(metadata);
            }
        } catch (SQLException exception) {
            throw new GetMetadataException("Error while getting table metadata. Message = [%s]" + exception.getMessage());
        }

        return tableMetadata;
    }

    @Override
    public List<ColumnMetadata> getColumns(GetColumnsAction action) {
        Asserts.isNotNull(action);

        DataSource dataSource = dataSourceService.getOrCreateDataSource(action.getConnectionInfo());
        String databaseName = action.getConnectionInfo().getDatabaseName();
        String schema = action.getConnectionInfo().getSchemaName();
        String tableName = action.getTableName();
        DatabaseType databaseType = action.getConnectionInfo().getDatabaseType();

        List<String> primaryKeyColumnNames = getPrimaryKeyColumnNames(dataSource, databaseType, tableName);
        List<ColumnMetadata> columns = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = resultSetService.getForColumns(connection, databaseName, schema, tableName, databaseType)) {
            while (resultSet.next()) {
                ColumnMetadata columnMetadata = new ColumnMetadata();
                columnMetadata.setDatabase(resultSet.getString("TABLE_CAT"));
                columnMetadata.setSchemaName(resultSet.getString("TABLE_SCHEM"));
                columnMetadata.setTableName(resultSet.getString("TABLE_NAME"));
                columnMetadata.setColumnName(resultSet.getString("COLUMN_NAME"));
                columnMetadata.setDataType(resultSet.getString("TYPE_NAME"));
                columnMetadata.setOrderIndex(resultSet.getInt("ORDINAL_POSITION"));
                columnMetadata.setSize(resultSet.getInt("COLUMN_SIZE"));
                columnMetadata.setGeneratedAlways("YES".equals(resultSet.getString("IS_AUTOINCREMENT")));
                if (primaryKeyColumnNames.contains(resultSet.getString("COLUMN_NAME"))) {
                    columnMetadata.setPrimaryKey(true);
                }

                columns.add(columnMetadata);
            }
        } catch (SQLException exception) {
            throw new GetMetadataException("Error while getting columns metadata. Message = [%s]" + exception.getMessage());
        }

        return columns;
    }

    @Override
    public RelatedTables getRelatedTables(GetRelatedTables getRelatedTables) {
        Asserts.isNotNull(getRelatedTables);

        VisorConnectionDataSource dataSource = dataSourceService.getOrCreateDataSource(getRelatedTables.getConnectionInfo());

        List<String> relatedTables = mapperService
                .getMapper(dataSource, getRelatedTables.getConnectionInfo().getDatabaseType())
                .getRelatedTables(getRelatedTables.getTableName(), getRelatedTables.getConnectionInfo().getSchemaName());
        return new RelatedTables()
                .setOwnerTableName(getRelatedTables.getTableName())
                .setRelatedTablesNames(relatedTables);
    }

    @Override
    public List<String> getPrimaryKeyColumnNames(DataSource dataSource,
                                                 DatabaseType databaseType,
                                                 String tableName) {
        List<String> columnNames = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = resultSetService.getForPrimaryKeyColumns(connection, tableName)) {
            while (resultSet.next()) {
                columnNames.add(resultSet.getString("COLUMN_NAME"));
            }
        } catch (SQLException exception) {
            throw new GetMetadataException(
                    "Error while getting primary key columns metadata. Message = [%s]" + exception.getMessage());
        }
        return columnNames;
    }

    @Override
    public void checkConnection(ConnectionInfo connectionInfo) {
        Asserts.isNotNull(connectionInfo);

        DataSource source = dataSourceService.createSimpleDriverDataSource(connectionInfo);

        try (Connection datasourceConnection = source.getConnection()) {
            Assert.isInstanceOf(Connection.class, datasourceConnection);
        } catch (Exception exception) {
            String errorMessage = String.format(
                    "Error during check database connection. Db unavailable. Reason = [%s]",
                    exception.getMessage()
            );
            throw new CheckConnectionException(errorMessage);
        }
    }

    @Override
    public void insertValues(InsertValuesAction insertValuesAction) {
        Asserts.isNotNull(insertValuesAction);

        try {
            byte[] content = insertValuesAction.getContent();
            ConnectionInfo connectionInfo = insertValuesAction.getConnectionInfo();
            DataSource dataSource = dataSourceService.getOrCreateDataSource(connectionInfo);

            Table table = parseService.parse(content);
            if (!table.isEmpty()) {
                namedParameterJdbcTemplate.getJdbcTemplate().setDataSource(dataSource);

                GenerateQueryAction action = new GenerateQueryAction()
                        .setColumnsNames(table.getColumnNames())
                        .setDatabaseType(connectionInfo.getDatabaseType())
                        .setTableName(insertValuesAction.getTargetTableName());

                String insertQuery = queryService.generateQuery(action);
                Map<String, ?>[] batchValues = convertToBatchValues(
                        table,
                        insertValuesAction.getColumnNamesPerMetaInfo(),
                        action.getDatabaseType());
                namedParameterJdbcTemplate.batchUpdate(insertQuery, batchValues);
            }
        } catch (RuntimeException exception) {
            throw new InsertValuesException(exception.getMessage());
        }
    }

    private Map<String, ?>[] convertToBatchValues(Table table,
                                                  Map<String, ColumnMetaInfo> columnNamesPerMetaInfo,
                                                  DatabaseType type) {
        Map<String, ?>[] batchValues = new Map[table.getRows().size()];
        TypeConverterService converterService = typeConverterServicesManager.get(type);

        int i = 0;
        for (Row row : table.getRows()) {
            Map<String, Object> rowValues = new HashMap<>(row.getValues().size());
            row.getValues().forEach(cellValue -> {
                String columnName = cellValue.getColumnName();
                String value = cellValue.getValue();
                ColumnMetaInfo columnMetaInfo = columnNamesPerMetaInfo.get(columnName);

                ConvertAction convertAction = new ConvertAction(
                        value,
                        columnMetaInfo.getColumnTypeName());

                if (StringUtils.isNotBlank(columnMetaInfo.getDateTimePattern())) {
                    convertAction.setDateTimeFormat(columnMetaInfo.getDateTimePattern());
                }

                rowValues.put(
                        CryptoUtils.hash(cellValue.getColumnName()), converterService.convert(convertAction));
            });
            batchValues[i++] = rowValues;
        }

        return batchValues;
    }
}
