package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.services.dto.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.GetRelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.RelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;

import javax.sql.DataSource;
import java.util.List;

/**
 * Сервис для получения мета-информации из БД
 * Под "мета-информацией" подразумевается: Таблицы, колонки, ограничения, связи
 */
public interface MetadataService {
    List<TableMetadata> getTables(DataSource source, ConnectionInfo info);

    RelatedTables getRelatedTables(GetRelatedTables getRelatedTables);
}
