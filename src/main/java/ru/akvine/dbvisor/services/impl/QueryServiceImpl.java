package ru.akvine.dbvisor.services.impl;

import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.QueryService;
import ru.akvine.dbvisor.services.dto.GenerateQueryAction;
import ru.akvine.dbvisor.utils.CryptoUtils;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class QueryServiceImpl implements QueryService {
    private static final String INSERT_QUERY_FORMAT_COMMON = "INSERT INTO %s (%s) VALUES (%s)";

    @Override
    public String generateQuery(GenerateQueryAction action) {
        Collection<String> columnNames = action.getColumnsNames();
        return String.format(
                INSERT_QUERY_FORMAT_COMMON,
                action.getTableName(),
                columnNames.stream()
                        .map(columnName -> toEscapedColumnName(columnName, action.getDatabaseType()))
                        .collect(Collectors.joining(",")),
                columnNames.stream().map(columnName -> ':' + CryptoUtils.hash(columnName))
                        .collect(Collectors.joining(", "))

        );
    }

    private static String toEscapedColumnName(String columnName, DatabaseType type) {
        switch (type) {
            case POSTGRESQL -> {
                return '"' + columnName + '"';
            }
            default -> {
                return columnName;
            }
        }
    }
}
