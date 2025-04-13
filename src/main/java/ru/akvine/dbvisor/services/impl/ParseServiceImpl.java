package ru.akvine.dbvisor.services.impl;

import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.services.ParseService;
import ru.akvine.dbvisor.services.dto.CellValue;
import ru.akvine.dbvisor.services.dto.Row;
import ru.akvine.dbvisor.services.dto.Table;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParseServiceImpl implements ParseService {
    @Override
    public Table parse(byte[] content) {
        List<String[]> lines;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8))) {

            lines = reader.lines()
                    .map(line -> line.split(";", -1))
                    .toList();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        if (lines.isEmpty()) {
            return new Table();
        }

        String[] headers = lines.getFirst();
        List<Row> rowList = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] rowData = lines.get(i);
            List<CellValue> cellValues = new ArrayList<>();

            for (int j = 0; j < headers.length; j++) {
                String columnName = headers[j];
                String rawValue = j < rowData.length ? rowData[j] : null;

                cellValues.add(new CellValue(columnName, rawValue));
            }

            rowList.add(new Row(cellValues));
        }

        return new Table()
                .setColumnNames(List.of(headers))
                .setRows(rowList);
    }
}
