package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.services.dto.metadata.ListConstraintsResult;
import ru.akvine.dbvisor.services.dto.metadata.GetConstraints;

public interface MetadataService {
    ListConstraintsResult getConstraints(GetConstraints action);
}
