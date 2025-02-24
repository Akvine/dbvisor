package ru.akvine.dbvisor.controllers.dto.connection;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateConnectionRequest {
    private String databaseName;

    private String schema;

    @NotBlank
    private String connectionName;

    @NotBlank
    private String databaseType;

    @NotBlank
    private String host;

    @NotBlank
    private String port;

    @ToString.Exclude
    private String username;

    @ToString.Exclude
    private String password;
}
