package ru.akvine.dbvisor.services.dto.metadata;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;

@Data
@Accessors(chain = true)
public class GetConstraints {
    private String tableName;
    private String columnName;
    private ConnectionInfo connectionInfo;
}
