package ru.akvine.dbvisor.services.dto.metadata;

public interface MetaObject {
    String getCreateStatement();

    String getDropStatement();

    String getEnableStatement();

    String getDisableStatement();

    String getTableName();
}
