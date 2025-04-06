package ru.akvine.dbvisor.controllers.dto.database;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;

@Data
@Accessors(chain = true)
public class GetColumnsRequest {
    @NotNull
    private ConnectionRequest connection;

    @NotBlank
    private String tableName;
}
