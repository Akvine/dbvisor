package ru.akvine.dbvisor.controllers.meta;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.akvine.dbvisor.controllers.dto.common.Response;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;

@RequestMapping(value = "/connections")
public interface ConnectionControllerMeta {

    @GetMapping
    Response list();

    @PostMapping
    Response create(@RequestBody @Valid ConnectionRequest request);

    @GetMapping("/check/{connectionName}")
    Response check(@PathVariable("connectionName") String connectionName);
}
