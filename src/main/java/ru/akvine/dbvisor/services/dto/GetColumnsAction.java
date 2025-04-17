package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;

@Data
@Accessors(chain = true)
public class GetColumnsAction {
    private ConnectionInfo connectionInfo;
    private String tableName;
}
