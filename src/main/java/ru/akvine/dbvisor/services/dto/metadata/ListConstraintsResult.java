package ru.akvine.dbvisor.services.dto.metadata;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.compozit.commons.visor.ConstraintType;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListConstraintsResult {
    private List<ConstraintType> constraintTypes;
    @Nullable
    private String targetColumnNameForForeignKey;
    @Nullable
    private String targetTableNameForForeignKey;
}
