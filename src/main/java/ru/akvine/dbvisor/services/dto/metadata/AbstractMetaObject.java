package ru.akvine.dbvisor.services.dto.metadata;

public abstract class AbstractMetaObject implements MetaObject {
    protected String name;

    protected Boolean disabled;

    protected String tableName;

    protected String relatedTableName;

    protected String createStatement;

    protected String dropStatement;

    protected String enableStatement;

    protected String disableStatement;
}
