package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetaInfo;

import java.util.Map;

@Data
@Accessors(chain = true)
public class InsertValuesAction {
    private Map<String, ColumnMetaInfo> columnNamesPerMetaInfo;
    private String targetTableName;
    private byte[] content;
    private ConnectionInfo connectionInfo;
}
