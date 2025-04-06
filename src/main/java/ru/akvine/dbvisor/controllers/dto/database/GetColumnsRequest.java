package ru.akvine.dbvisor.controllers.dto.database;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.controllers.dto.connection.ConnectionRequest;

@Data
@Accessors(chain = true)
public class GetColumnsRequest {
    private ConnectionRequest connection;
    private String tableName;
}
