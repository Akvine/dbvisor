package ru.akvine.dbvisor.controllers.dto.database;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.controllers.dto.ColumnMetaInfoDto;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionDto;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class InsertValuesRequest {
    @NotBlank
    private String tableName;

    @NotNull
    private ConnectionDto connection;

    @NotNull
    private byte[] content;

    @NotNull
    private Map<String, ColumnMetaInfoDto> columnsMetaInfo = new HashMap<>();
}
