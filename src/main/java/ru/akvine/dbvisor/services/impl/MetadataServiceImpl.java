package ru.akvine.dbvisor.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.compozit.commons.visor.ConstraintType;
import ru.akvine.dbvisor.services.DataSourceService;
import ru.akvine.dbvisor.services.MapperService;
import ru.akvine.dbvisor.services.MetadataService;
import ru.akvine.dbvisor.services.dto.metadata.Constraint;
import ru.akvine.dbvisor.services.dto.metadata.ForeignConstraintInfo;
import ru.akvine.dbvisor.services.dto.metadata.GetConstraints;
import ru.akvine.dbvisor.services.dto.metadata.ListConstraintsResult;
import ru.akvine.dbvisor.services.mappers.CommonMapper;
import ru.akvine.dbvisor.utils.Asserts;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetadataServiceImpl implements MetadataService {
    private final DataSourceService dataSourceService;
    private final MapperService mapperService;

    @Override
    public ListConstraintsResult getConstraints(GetConstraints action) {
        Asserts.isNotNull(action);
        ListConstraintsResult result = new ListConstraintsResult();

        DataSource dataSource = dataSourceService.getOrCreateDataSource(action.getConnectionInfo());
        CommonMapper mapper = mapperService.getMapper(dataSource, action.getConnectionInfo().getDatabaseType());

        String tableName = action.getTableName();
        String columnName = action.getColumnName();
        String schema = action.getConnectionInfo().getSchemaName();

        List<ConstraintType> constraints = new ArrayList<>();
        try {
            addConstraintType(constraints, mapper.getPKConstraintForColumn(tableName, columnName, schema));
            addConstraintType(constraints, mapper.getFKConstraintForColumn(tableName, columnName, schema));
            addConstraintType(constraints, mapper.getNotNullConstraintForColumn(tableName, columnName, schema));
            addConstraintType(constraints, mapper.getDefaultConstraintForColumn(tableName, columnName, schema));
            addConstraintType(constraints, mapper.getUniqueConstraintForColumn(tableName, columnName, schema));
            addConstraintType(constraints, mapper.getCheckConstraintForColumn(tableName, columnName, schema));
            addConstraintType(constraints, mapper.getIndexConstraintForColumn(tableName, columnName, schema));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        if (constraints.contains(ConstraintType.FOREIGN_KEY)) {
            ForeignConstraintInfo info = mapper.getTargetForeignColumnNameAndTableName(tableName, columnName, schema);
            result.setTargetColumnNameForForeignKey(info.getReferencedColumnName());
            result.setTargetTableNameForForeignKey(info.getReferencedTableName());
        }

        result.setConstraintTypes(constraints);
        return result;
    }

    private void addConstraintType(List<ConstraintType> constraints, Constraint constraintToAdd) {
        if (constraintToAdd != null) {
            constraints.add(constraintToAdd.getConstraintType());
        }
    }
}
