package ru.akvine.dbvisor.controllers.dto.metadata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;

@Data
@Accessors(chain = true)
public class ListConstraintsRequest {
    @NotBlank
    private String tableName;

    @NotBlank
    private String columnName;

    @NotNull
    private ConnectionRequest connectionInfo;
}
