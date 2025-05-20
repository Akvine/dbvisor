package ru.akvine.dbvisor.services.dto.metadata;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.ConstraintType;

@Setter
@Getter
@Accessors(chain = true)
public class Constraint extends AbstractMetaObject {
    private ConstraintType constraintType;

    @Override
    public String getCreateStatement() {
        return this.createStatement;
    }

    @Override
    public String getDropStatement() {
        return this.dropStatement;
    }

    @Override
    public String getEnableStatement() {
        return this.enableStatement;
    }

    @Override
    public String getDisableStatement() {
        return this.disableStatement;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }
}
