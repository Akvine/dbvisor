package ru.akvine.dbvisor.controllers.converters;

import org.springframework.stereotype.Component;
import ru.akvine.dbvisor.controllers.dto.metadata.ListConstraintsRequest;
import ru.akvine.dbvisor.services.dto.metadata.GetConstraints;

@Component
public class MetadataConverter extends ConnectionConverter {
    public GetConstraints convertToGetConstraints(ListConstraintsRequest request) {
        return new GetConstraints()
                .setTableName(request.getTableName())
                .setColumnName(request.getColumnName())
                .setConnectionInfo(convert(request.getConnectionInfo()));
    }
}
