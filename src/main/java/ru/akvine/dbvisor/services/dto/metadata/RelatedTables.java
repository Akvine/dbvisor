package ru.akvine.dbvisor.services.dto.metadata;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RelatedTables {
    private String ownerTableName;
    private List<String> relatedTablesNames;
}
