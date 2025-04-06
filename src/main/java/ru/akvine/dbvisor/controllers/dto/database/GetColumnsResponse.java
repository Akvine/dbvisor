package ru.akvine.dbvisor.controllers.dto.database;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.controllers.dto.common.SuccessfulResponse;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetColumnsResponse extends SuccessfulResponse {
    private List<ColumnMetadataDto> columns;
}
