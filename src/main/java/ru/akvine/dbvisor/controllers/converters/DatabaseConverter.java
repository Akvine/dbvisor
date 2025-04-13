package ru.akvine.dbvisor.controllers.converters;

import org.springframework.stereotype.Component;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;
import ru.akvine.dbvisor.controllers.dto.database.*;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.GetColumnsAction;
import ru.akvine.dbvisor.services.dto.InsertValuesAction;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetadata;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.utils.FileUtils;

import java.util.List;

@Component
public class DatabaseConverter {
    public ConnectionInfo convertToConnectionInfo(ConnectionRequest request) {
        Asserts.isNotNull(request);
        return new ConnectionInfo()
                .setDatabaseName(request.getDatabaseName())
                .setHost(request.getHost())
                .setPort(request.getPort())
                .setSchemaName(request.getSchema())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setDatabaseType(DatabaseType.from(request.getDatabaseType()));
    }

    public InsertValuesAction convertToInsertValuesAction(InsertValuesRequest request) {
        return new InsertValuesAction()
                .setContent(request.getContent())
                .setTargetTableName(request.getTableName())
                .setConnectionInfo(new ConnectionInfo()
                        .setDatabaseName(request.getConnection().getDatabaseName())
                        .setDatabaseType(DatabaseType.from(request.getConnection().getDatabaseType()))
                        .setSchemaName(request.getConnection().getSchema())
                        .setHost(request.getConnection().getHost())
                        .setUsername(request.getConnection().getUsername())
                        .setPassword(request.getConnection().getPassword())
                        .setPort(request.getConnection().getPort()));
    }

    public GetColumnsAction convertToGetColumnsActions(GetColumnsRequest request) {
        Asserts.isNotNull(request);
        return new GetColumnsAction()
                .setConnectionInfo(convertToConnectionInfo(request.getConnection()))
                .setTableName(request.getTableName());
    }

    public GetTableResponse convertToGetTableResponse(List<TableMetadata> tables) {
        return new GetTableResponse().setTables(tables.stream().map(this::buildTableMetadataDto).toList());
    }

    private TableMetadataDto buildTableMetadataDto(TableMetadata tableMetadata) {
        return new TableMetadataDto()
                .setDatabase(tableMetadata.getDatabase())
                .setSchema(tableMetadata.getSchema())
                .setTableName(tableMetadata.getTableName());
    }

    public GetColumnsResponse convertToGetColumnsResponse(List<ColumnMetadata> columns) {
        return new GetColumnsResponse().setColumns(columns.stream().map(this::buildColumnMetadataDto).toList());
    }

    private ColumnMetadataDto buildColumnMetadataDto(ColumnMetadata columnMetadata) {
        return new ColumnMetadataDto()
                .setColumnName(columnMetadata.getColumnName())
                .setSize(columnMetadata.getSize())
                .setOrderIndex(columnMetadata.getOrderIndex())
                .setTableName(columnMetadata.getTableName())
                .setColumnName(columnMetadata.getColumnName())
                .setSchemaName(columnMetadata.getSchemaName())
                .setDataType(columnMetadata.getDataType())
                .setDatabase(columnMetadata.getDatabase())
                .setPrimaryKey(columnMetadata.isPrimaryKey())
                .setGeneratedAlways(columnMetadata.isGeneratedAlways());
    }
}
