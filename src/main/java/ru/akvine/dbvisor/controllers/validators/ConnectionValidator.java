package ru.akvine.dbvisor.controllers.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.akvine.compozit.commons.ConnectionRequest;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.validators.DatabaseTypeValidator;

@Component
@RequiredArgsConstructor
public class ConnectionValidator {
    private final DatabaseTypeValidator databaseTypeValidator;

    public void verifyCreateConnectionRequest(ConnectionRequest request) {
        Asserts.isNotNull(request);
        databaseTypeValidator.validate(request.getDatabaseType());
    }
}
