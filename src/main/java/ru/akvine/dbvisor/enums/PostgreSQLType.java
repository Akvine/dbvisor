package ru.akvine.dbvisor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import ru.akvine.dbvisor.exceptions.UnknownTypeException;

@Getter
@AllArgsConstructor
public enum PostgreSQLType {
    BOOL("bool"),
    BIT("bit"),
    INT8("int8"),
    BIGSERIAL("bigserial"),
    OID("oid"),
    BYTEA("bytea"),
    CHAR("char"),
    BPCHAR("bpchar"),
    NUMERIC("numeric"),
    INT4("int4"),
    SERIAL("serial"),
    INT2("int2"),
    SMALLSERIAL("smallserial"),
    SERIAL4("serial4"),
    FLOAT4("float4"),
    FLOAT8("float8"),
    MONEY("money"),
    NAME("name"),
    TEXT("text"),
    VARCHAR("varchar"),
    DATE("date"),
    TIME("time"),
    TIMETZ("timetz"),
    TIMESTAMP("timestamp"),
    TIMESTAMPZ("timestampz");

    private final String dataType;

    public static PostgreSQLType from(String type) {
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("Type can't be blank");
        }

        for (PostgreSQLType postgreSQLType : values()) {
            if (postgreSQLType.getDataType().equalsIgnoreCase(type)) {
                return postgreSQLType;
            }
        }

        String errorMessage = String.format(
                "Error while parsing mapping raw type = [%s] to [%s] type",
                type,
                PostgreSQLType.class.getSimpleName()
        );
        throw new UnknownTypeException(errorMessage);
    }
}
