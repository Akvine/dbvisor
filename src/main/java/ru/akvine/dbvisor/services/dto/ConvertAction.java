package ru.akvine.dbvisor.services.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConvertAction {
    private String value;
    private String columnTypeName;
    @Nullable
    private String dateTimeFormat;

    public ConvertAction(String value, String columnTypeName) {
        this.value = value;
        this.columnTypeName = columnTypeName;
    }
}
