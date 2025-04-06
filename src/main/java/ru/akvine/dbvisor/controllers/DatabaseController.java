package ru.akvine.dbvisor.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.dbvisor.controllers.converters.DatabaseConverter;
import ru.akvine.dbvisor.controllers.dto.common.Response;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;
import ru.akvine.dbvisor.controllers.dto.database.GetColumnsRequest;
import ru.akvine.dbvisor.controllers.meta.DatabaseControllerMeta;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.MetadataService;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.GetColumnsAction;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetadata;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DatabaseController implements DatabaseControllerMeta {
    private final MetadataService metadataService;
    private final DataSourceService dataSourceService;
    private final DatabaseConverter databaseConverter;

    @Override
    public Response getTables(@Valid @RequestBody ConnectionRequest request) {
        ConnectionInfo connectionInfo = databaseConverter.convertToConnectionInfo(request);
        DataSource dataSource = dataSourceService.createHikariDataSource(connectionInfo);
        List<TableMetadata> tables = metadataService.getTables(dataSource, connectionInfo);
        return databaseConverter.convertToGetTableResponse(tables);
    }

    @Override
    public Response getColumns(@Valid @RequestBody GetColumnsRequest request) {
        ConnectionInfo connectionInfo = databaseConverter.convertToConnectionInfo(request.getConnection());
        DataSource dataSource = dataSourceService.createHikariDataSource(connectionInfo);
        GetColumnsAction getColumnsAction = new GetColumnsAction()
                .setDatabase(connectionInfo.getDatabaseName())
                .setType(connectionInfo.getDatabaseType())
                .setDataSource(dataSource)
                .setSchemaName(connectionInfo.getSchemaName())
                .setTableName(request.getTableName());
        List<ColumnMetadata> columns = metadataService.getColumns(getColumnsAction);
        return databaseConverter.convertToGetColumnsResponse(columns);
    }
}
