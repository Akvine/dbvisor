package ru.akvine.dbvisor.services.dto.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ForeignConstraintInfo {
    private String referencedColumnName;
    private String referencedTableName;
}
