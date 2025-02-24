--liquibase formatted sql logicalFilePath:db/changelog/database-changelog.sql

--changeset akvine:DBVISOR-1-1
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where upper(table_name) = 'CONNECTION_ENTITY'
CREATE TABLE CONNECTION_ENTITY (
    ID BIGINT               PRIMARY KEY,
    UUID                    VARCHAR(255)        NOT NULL,
    CONNECTION_NAME         VARCHAR(255)        NOT NULL,
    DATABASE_NAME           VARCHAR(255),
    SCHEMA                  VARCHAR(255),
    HOST                    VARCHAR(255)        NOT NULL,
    PORT                    VARCHAR(255)        NOT NULL,
    USERNAME                VARCHAR(255),
    PASSWORD                VARCHAR(255),
    DATABASE_TYPE           VARCHAR(255)        NOT NULL,
    CREATED_DATE            TIMESTAMP           NOT NULL,
    UPDATED_DATE            TIMESTAMP,
    IS_DELETED              BOOLEAN             NOT NULL,
    DELETED_DATE            TIMESTAMP
);
CREATE SEQUENCE SEQ_CONNECTION_ENTITY START WITH 1 INCREMENT BY 1000;
CREATE UNIQUE INDEX UX_CONNECTION_ENTITY_ID ON CONNECTION_ENTITY (ID);
CREATE UNIQUE INDEX UX_CONNECTION_ENTITY_NAME ON CONNECTION_ENTITY (CONNECTION_NAME);
CREATE UNIQUE INDEX UX_CONNECTION_ENTITY_UUID ON CONNECTION_ENTITY (UUID);
--rollback not required