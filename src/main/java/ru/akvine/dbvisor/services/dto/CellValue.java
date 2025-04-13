package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class CellValue {
    private final String columnName;
    private final String value;
}
