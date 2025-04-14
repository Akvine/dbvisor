package ru.akvine.dbvisor.services.dto.metadata;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ColumnMetaInfo {
    private String columnTypeName;
    private String dateTimePattern;
}
