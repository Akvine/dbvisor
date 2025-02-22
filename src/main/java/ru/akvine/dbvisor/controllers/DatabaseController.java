package ru.akvine.dbvisor.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.dbvisor.controllers.dto.GetTablesRequest;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.MetadataService;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;

import javax.sql.DataSource;

@RequestMapping(value = "/databases")
@RestController
@RequiredArgsConstructor
public class DatabaseController {
    private final MetadataService metadataService;
    private final DataSourceService dataSourceService;

    @GetMapping
    public ResponseEntity<?> getMetadata(@RequestBody @Valid GetTablesRequest request) {
        ConnectionInfo info = new ConnectionInfo()
                .setHost(request.getHost())
                .setPort(request.getPort())
                .setPassword(request.getPassword())
                .setUsername(request.getUsername())
                .setSchemaName(request.getSchema())
                .setDatabaseName(request.getDatabaseName())
                .setDatabaseType(DatabaseType.POSTGRESQL);
        DataSource dataSource = dataSourceService.create(info);
        return ResponseEntity.ok().body(metadataService.getTables(dataSource, info));
    }
}
