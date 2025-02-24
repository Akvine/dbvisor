package ru.akvine.dbvisor.controllers.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.dbvisor.controllers.dto.connection.CreateConnectionRequest;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.validators.DatabaseTypeValidator;

@Component
@RequiredArgsConstructor
public class ConnectionValidator {
    private final DatabaseTypeValidator databaseTypeValidator;

    public void verifyCreateConnectionRequest(CreateConnectionRequest request) {
        Asserts.isNotNull(request);
        databaseTypeValidator.validate(request.getDatabaseType());
    }
}
