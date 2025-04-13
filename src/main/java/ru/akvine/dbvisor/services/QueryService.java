package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.services.dto.GenerateQueryAction;

public interface QueryService {
    String generateQuery(GenerateQueryAction action);
}
