package ru.akvine.dbvisor.controllers.converters;

import org.springframework.stereotype.Component;
import ru.akvine.compozit.commons.GetRelatedTablesResponse;
import ru.akvine.dbvisor.controllers.dto.ColumnMetaInfoDto;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;
import ru.akvine.dbvisor.controllers.dto.database.*;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.GetColumnsAction;
import ru.akvine.dbvisor.services.dto.GetRelatedTables;
import ru.akvine.dbvisor.services.dto.InsertValuesAction;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetaInfo;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetadata;
import ru.akvine.dbvisor.services.dto.metadata.RelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;
import ru.akvine.dbvisor.utils.Asserts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public GetRelatedTables convertToGetRelatedTables(GetRelatedTablesRequest request) {
        return new GetRelatedTables()
                .setConnectionInfo(convertToConnectionInfo(request.getConnectionInfo()))
                .setTableName(request.getTableName());
    }

    public GetRelatedTablesResponse convertToGetRelatedTablesResponse(RelatedTables relatedTables) {
        return new GetRelatedTablesResponse()
                .setOwnerTableName(relatedTables.getOwnerTableName())
                .setRelatedTablesNames(relatedTables.getRelatedTablesNames());
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
                        .setPort(request.getConnection().getPort()))
                .setColumnNamesPerMetaInfo(buildMap(request.getColumnsMetaInfo()));
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

    private Map<String, ColumnMetaInfo> buildMap(Map<String, ColumnMetaInfoDto> metaInfoDtoMap) {
        Map<String, ColumnMetaInfo> result = new HashMap<>();
        for (Map.Entry<String, ColumnMetaInfoDto> entry : metaInfoDtoMap.entrySet()) {
            result.put(entry.getKey(), buildColumnMetaInfo(entry.getValue()));
        }

        return result;
    }

    private ColumnMetaInfo buildColumnMetaInfo(ColumnMetaInfoDto columnMetaInfoDto) {
        return new ColumnMetaInfo()
                .setColumnTypeName(columnMetaInfoDto.getColumnTypeName())
                .setDateTimePattern(columnMetaInfoDto.getDateTimeFormat());
    }
}
