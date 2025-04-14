package ru.akvine.dbvisor.services.converters;

import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.enums.PostgreSQLType;
import ru.akvine.dbvisor.exceptions.ConvertTypeException;
import ru.akvine.dbvisor.services.TypeConverterService;
import ru.akvine.dbvisor.services.dto.ConvertAction;
import ru.akvine.dbvisor.utils.Asserts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class PostgreSQLTypeConverterService implements TypeConverterService {

    @Override
    public Object convert(ConvertAction action) {
        Asserts.isNotNull(action);

        DateTimeFormatter formatter;
        String value = action.getValue();
        try {
            PostgreSQLType postgreSQLType = PostgreSQLType.from(action.getColumnTypeName());
            switch (postgreSQLType) {
                case BOOL:
                case BIT:
                    return Boolean.parseBoolean(value);
                case INT8:
                case BIGSERIAL:
                case OID:
                    return Long.parseLong(value);
                case BYTEA:
                    return value.getBytes();
                case CHAR:
                case BPCHAR:
                case NAME:
                case TEXT:
                case VARCHAR:
                    return value;
                case INT4:
                case SERIAL:
                    return Integer.parseInt(value);
                case INT2:
                case SMALLSERIAL:
                    return Short.parseShort(value);
                case FLOAT4:
                    return Float.parseFloat(value);
                case FLOAT8:
                case MONEY:
                    return Double.parseDouble(value);
                case TIMESTAMP:
                     formatter = DateTimeFormatter.ofPattern(action.getDateTimeFormat());
                    return LocalDateTime.parse(value, formatter);
                case DATE:
                    formatter = DateTimeFormatter.ofPattern(action.getDateTimeFormat());
                    return LocalDate.parse(value, formatter);
                case TIME:
                    formatter = DateTimeFormatter.ofPattern(action.getDateTimeFormat());
                    return LocalTime.parse(value, formatter);
                default:
                    return value;
            }
        } catch (RuntimeException exception) {
            String errorMessage = String.format(
                    "Error while convert raw type = [%s] to [%s]. Message = [%s]",
                    value, PostgreSQLTypeConverterService.class.getSimpleName(), exception.getMessage()
            );
           throw new ConvertTypeException(errorMessage, exception);
        }
    }

    @Override
    public DatabaseType getByDatabaseType() {
        return DatabaseType.POSTGRESQL;
    }
}
