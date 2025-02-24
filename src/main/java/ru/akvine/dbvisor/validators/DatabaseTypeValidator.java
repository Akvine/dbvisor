package ru.akvine.dbvisor.validators;

import org.springframework.stereotype.Component;
import ru.akvine.dbvisor.constants.ErrorCodes;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.exceptions.validation.ValidationException;

@Component
public class DatabaseTypeValidator implements Validator<String> {
    @Override
    public void validate(String databaseType) {
        try {
            DatabaseType.from(databaseType);
        } catch (RuntimeException exception) {
            throw new ValidationException(
                    ErrorCodes.Validation.DATABASE_TYPE_INVALID_ERROR,
                    exception.getMessage()
            );
        }
    }
}
