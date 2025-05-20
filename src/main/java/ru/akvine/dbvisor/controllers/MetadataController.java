package ru.akvine.dbvisor.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.akvine.compozit.commons.dto.Response;
import ru.akvine.dbvisor.controllers.converters.MetadataConverter;
import ru.akvine.dbvisor.controllers.dto.metadata.ListConstraintsRequest;
import ru.akvine.dbvisor.controllers.dto.metadata.ListConstraintsResponse;
import ru.akvine.dbvisor.controllers.meta.MetadataControllerMeta;
import ru.akvine.dbvisor.enums.ConstraintType;
import ru.akvine.dbvisor.services.MetadataService;
import ru.akvine.dbvisor.services.dto.metadata.GetConstraints;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MetadataController implements MetadataControllerMeta {
    private final MetadataService metadataService;
    private final MetadataConverter metadataConverter;

    @Override
    public Response listConstraints(@RequestBody @Valid ListConstraintsRequest request) {
        GetConstraints action = metadataConverter.convertToGetConstraints(request);
        List<ConstraintType> result = metadataService.getConstraints(action);
        return new ListConstraintsResponse()
                .setConstraintTypes(result);
    }
}
