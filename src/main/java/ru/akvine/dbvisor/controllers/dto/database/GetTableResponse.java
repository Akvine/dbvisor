package ru.akvine.dbvisor.controllers.dto.database;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.compozit.commons.dto.SuccessfulResponse;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetTableResponse extends SuccessfulResponse {
    private List<TableMetadataDto> tables;
}
