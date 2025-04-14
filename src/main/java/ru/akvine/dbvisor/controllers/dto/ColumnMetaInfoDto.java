package ru.akvine.dbvisor.controllers.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ColumnMetaInfoDto {
    private String columnTypeName;
    private String dateTimeFormat;
}
