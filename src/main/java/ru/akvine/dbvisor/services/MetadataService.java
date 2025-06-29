package ru.akvine.dbvisor.services;

import ru.akvine.compozit.commons.visor.ConstraintType;
import ru.akvine.dbvisor.services.dto.metadata.GetConstraints;

import java.util.List;

public interface MetadataService {
    List<ConstraintType> getConstraints(GetConstraints action);
}
