package ru.akvine.dbvisor.enums;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ConstraintType {
    TRIGGER(List.of()),
    FOREIGN_KEY(List.of("f", "F", "R", "FOREIGN KEY")),
    UNIQUE(List.of("u", "U", "UNIQUE")),
    IDENTITY(List.of("IDENTITY")),
    INDEX(List.of("i")),
    PRIMARY_KEY(List.of("p", "P", "PRIMARY KEY")),
    CHECK(List.of("c", "C", "CHECK")),
    DEFAULT(List.of("d", "D")),
    NOT_NULL(List.of("n", "N"));

    private final List<String> codes;

    public static ConstraintType from(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Constraint type code can't be blank!");
        }

        for (ConstraintType type : values()) {
            List<String> codes = type.getCodes();

            for (String constraintCode : codes) {
                if (constraintCode.equals(code)) {
                    return type;
                }
            }
        }

        throw new UnsupportedOperationException("Constraint type with code = [" + code + "] is not supported by app!");
    }
}
