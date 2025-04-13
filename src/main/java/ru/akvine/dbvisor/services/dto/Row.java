package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class Row {
    private final List<CellValue> values;
}
