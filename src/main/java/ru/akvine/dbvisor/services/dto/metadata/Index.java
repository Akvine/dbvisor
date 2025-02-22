package ru.akvine.dbvisor.services.dto.metadata;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class Index extends AbstractMetaObject {
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
