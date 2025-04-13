package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.GetColumnsAction;
import ru.akvine.dbvisor.services.dto.GetRelatedTables;
import ru.akvine.dbvisor.services.dto.InsertValuesAction;
import ru.akvine.dbvisor.services.dto.metadata.ColumnMetadata;
import ru.akvine.dbvisor.services.dto.metadata.RelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;

import javax.sql.DataSource;
import java.util.List;

/**
 * Сервис для получения мета-информации из БД
 * Под "мета-информацией" подразумевается: Таблицы, колонки, ограничения, связи
 */
public interface DatabaseService {
    List<TableMetadata> getTables(DataSource source, ConnectionInfo info);

    List<ColumnMetadata> getColumns(GetColumnsAction action);

    RelatedTables getRelatedTables(GetRelatedTables getRelatedTables);

    List<String> getPrimaryKeyColumnNames(DataSource dataSource, DatabaseType databaseType, String tableName);

    void checkConnection(ConnectionInfo connectionInfo);

    void insertValues(InsertValuesAction insertValuesAction);
}
