package ru.akvine.dbvisor.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.exceptions.GetMetadataException;
import ru.akvine.dbvisor.services.MapperService;
import ru.akvine.dbvisor.services.MetadataService;
import ru.akvine.dbvisor.services.ResultSetService;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.GetColumnsAction;
import ru.akvine.dbvisor.services.dto.GetRelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetadata;
import ru.akvine.dbvisor.services.dto.metadata.RelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;
import ru.akvine.dbvisor.utils.Asserts;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetadataServiceImpl implements MetadataService {
    private final ResultSetService resultSetService;
    private final MapperService mapperService;

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

        DataSource dataSource = action.getDataSource();
        String database = action.getDatabase();
        String schema = action.getSchemaName();
        String tableName = action.getTableName();
        DatabaseType databaseType = action.getType();

        List<String> primaryKeyColumnNames = getPrimaryKeyColumnNames(dataSource, databaseType, tableName);
        List<ColumnMetadata> columns = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = resultSetService.getForColumns(connection, database, schema, tableName, databaseType)) {
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

        List<String> relatedTables = mapperService
                .getMapper(getRelatedTables.getDataSource(), getRelatedTables.getDatabaseType())
                .getRelatedTables(getRelatedTables.getTableName(), getRelatedTables.getSchema());
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
}
