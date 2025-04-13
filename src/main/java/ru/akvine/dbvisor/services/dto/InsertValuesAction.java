package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InsertValuesAction {
    private String targetTableName;
    private byte[] content;
    private ConnectionInfo connectionInfo;
}
