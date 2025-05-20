package ru.akvine.dbvisor.enums;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ConstraintType {
    TRIGGER("Trigger", List.of()),
    FOREIGN_KEY("Foreign key", List.of("f", "F", "R", "FOREIGN KEY")),
    UNIQUE("Unique", List.of("u", "U", "UNIQUE")),
    IDENTITY("Identity", List.of("IDENTITY")),
    INDEX("Index", List.of("i")),
    PRIMARY_KEY("Primary key", List.of("p", "P", "PRIMARY KEY")),
    CHECK("Check", List.of("c", "C", "CHECK")),
    DEFAULT("Default", List.of("d", "D")),
    NOT_NULL("Not null", List.of("n", "N"));

    private final String name;
    private final List<String> codes;

    public static ConstraintType fromName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Constraint type name can't be blank!");
        }

        for (ConstraintType type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }

        throw new UnsupportedOperationException("Constraint type with name = [" + name + "] is not supported by app!");
    }

    public static ConstraintType fromCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("Constraint type code can't be blank!");
        }

        for (ConstraintType type : values()) {
            List<String> codes = type.getCodes();

            if (codes.contains(code)) {
                return type;
            }
        }

        throw new UnsupportedOperationException("Constraint type with code = [" + code + "] is not supported by app!");
    }
}
