package ru.akvine.dbvisor.controllers.meta;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akvine.compozit.commons.dto.Response;
import ru.akvine.dbvisor.controllers.dto.metadata.ListConstraintsRequest;

@RequestMapping(value = "/metadata")
public interface MetadataControllerMeta {
    @PostMapping(value = "/constraints")
    Response listConstraints(@RequestBody @Valid ListConstraintsRequest request);
}
