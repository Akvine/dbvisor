package ru.akvine.dbvisor.controllers.meta;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.dbvisor.controllers.dto.common.Response;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;
import ru.akvine.dbvisor.controllers.dto.database.GetColumnsRequest;

@RequestMapping(value = "/databases")
public interface DatabaseControllerMeta {
    @GetMapping(value = "/tables")
    Response getTables(@RequestBody @Valid ConnectionRequest request);

    @GetMapping(value = "/columns")
    Response getColumns(@RequestBody @Valid GetColumnsRequest request);
}
