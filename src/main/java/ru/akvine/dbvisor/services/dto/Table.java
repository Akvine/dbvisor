package ru.akvine.dbvisor.services.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Table {
    private List<String> columnNames;
    private List<Row> rows;

    public Table() {
        rows = new ArrayList<>();
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(rows);
    }
}
