package ru.akvine.dbvisor.controllers.meta;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.compozit.commons.dto.Response;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;
import ru.akvine.dbvisor.controllers.dto.database.GetColumnsRequest;
import ru.akvine.dbvisor.controllers.dto.database.InsertValuesRequest;

@RequestMapping(value = "/databases")
public interface DatabaseControllerMeta {
    @GetMapping(value = "/tables")
    Response getTables(@RequestBody @Valid ConnectionRequest request);

    @GetMapping(value = "/columns")
    Response getColumns(@RequestBody @Valid GetColumnsRequest request);

    @GetMapping(value = "/connections/check")
    Response checkConnection(@RequestBody @Valid ConnectionRequest request);

    @PostMapping(value = "/data/insert")
    Response insertValues(@RequestBody @Valid InsertValuesRequest request);
}
