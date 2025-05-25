package ru.akvine.dbvisor.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.compozit.commons.ConnectionRequest;
import ru.akvine.compozit.commons.dto.Response;
import ru.akvine.compozit.commons.dto.SuccessfulResponse;
import ru.akvine.dbvisor.controllers.converters.DatabaseConverter;
import ru.akvine.dbvisor.controllers.dto.database.GetColumnsRequest;
import ru.akvine.dbvisor.controllers.dto.database.GetRelatedTablesRequest;
import ru.akvine.dbvisor.controllers.dto.database.InsertValuesRequest;
import ru.akvine.dbvisor.controllers.meta.DatabaseControllerMeta;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.DatabaseService;
import ru.akvine.dbvisor.services.dto.GetRelatedTables;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.GetColumnsAction;
import ru.akvine.dbvisor.services.dto.InsertValuesAction;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetadata;
import ru.akvine.dbvisor.services.dto.metadata.RelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DatabaseController implements DatabaseControllerMeta {
    private final DatabaseService databaseService;
    private final DataSourceService dataSourceService;
    private final DatabaseConverter databaseConverter;

    @Override
    public Response getTables(@Valid @RequestBody ConnectionRequest request) {
        ConnectionInfo connectionInfo = databaseConverter.convert(request);
        DataSource dataSource = dataSourceService.getOrCreateDataSource(connectionInfo);
        List<TableMetadata> tables = databaseService.getTables(dataSource, connectionInfo);
        return databaseConverter.convertToGetTableResponse(tables);
    }

    @Override
    public Response getRelatedTables(@RequestBody @Valid GetRelatedTablesRequest request) {
        GetRelatedTables action = databaseConverter.convertToGetRelatedTables(request);
        RelatedTables result = databaseService.getRelatedTables(action);
        return databaseConverter.convertToGetRelatedTablesResponse(result);
    }

    @Override
    public Response getColumns(@Valid @RequestBody GetColumnsRequest request) {
        GetColumnsAction action = databaseConverter.convertToGetColumnsActions(request);
        List<ColumnMetadata> columns = databaseService.getColumns(action);
        return databaseConverter.convertToGetColumnsResponse(columns);
    }

    @Override
    public Response checkConnection(@RequestBody @Valid ConnectionRequest request) {
        ConnectionInfo connectionInfo = databaseConverter.convert(request);
        databaseService.checkConnection(connectionInfo);
        return new SuccessfulResponse();
    }

    @Override
    public Response insertValues(@RequestBody @Valid InsertValuesRequest request) {
        InsertValuesAction insertValuesAction = databaseConverter.convertToInsertValuesAction(request);
        databaseService.insertValues(insertValuesAction);
        return new SuccessfulResponse();
    }
}
