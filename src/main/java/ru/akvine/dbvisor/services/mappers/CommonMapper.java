package ru.akvine.dbvisor.services.mappers;

import ru.akvine.dbvisor.services.dto.metadata.Constraint;
import ru.akvine.dbvisor.services.dto.metadata.ForeignConstraintInfo;
import ru.akvine.dbvisor.services.dto.metadata.Index;
import ru.akvine.dbvisor.services.dto.metadata.Trigger;

import java.util.List;

/**
 * Класс для получения метаинформации из таблицы (индексы, первичные ключи, ограничения и т.д.)
 * с помощью MyBatis
 */
public interface CommonMapper {

    /**
     * Реализация должна возвращать список уникальных Index (индексов) конкретной таблицы для конкретной БД
     * которые НЕ связаны с PRIMARY KEY и UNIQUE ограничениями, а были созданы сами
     * @param tableName наименование в БД
     * @param schemaName наименование схемы в БД
     * @param activeOnly возвращать только активные на данный момент индексы
     * @return список Index
     */
    List<Index> getUniqueIndexesByTable(String tableName, String schemaName, boolean activeOnly);

    /**
     * Реализация должна возвращать список НЕ уникальных Index (индексов) конкретной таблицы для конкретной БД
     * @param tableName наименование таблицы в БД
     * @param schemaName наименование схемы в БД
     * @param activeOnly возвращать только активные на данный момент индексы
     * @return список Index
     */
    List<Index> getNonUniqueIndexesByTable(String tableName, String schemaName, boolean activeOnly);

    /**
     * Реализация должна возвращать список Trigger (триггеров) конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблица в БД
     * @param schemaName наименование схемы в БД
     * @param activeOnly возвращать только активные на данный момент триггеры
     * @return список Trigger
     */
    List<Trigger> getTriggersByTable(String tableName, String schemaName, boolean activeOnly);

    /**
     * Реализация должна возвращать список PRIMARY KEY конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param schemaName наименование схемы в БД
     * @return список Constraint
     */
    List<Constraint> getPrimaryKeyByTable(String tableName, String schemaName);

    /**
     * Реализация должна возвращать список FOREIGN KEY конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param schemaName наименование схемы в БД
     * @return список Constraint
     */
    List<Constraint> getForeignKeyByTable(String tableName, String schemaName);

    /**
     * Реализация должна возвращать список UNIQUE конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param schemaName наименование схемы в БД
     * @return список Constraint
     */
    List<Constraint> getUniqueByTable(String tableName, String schemaName);

    /**
     * Реализация должна возвращать список CHECK конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param schemaName наименование схемы в БД
     * @return список Constraint
     */
    List<Constraint> getCheckByTable(String tableName, String schemaName);

    /**
     * Реализация должна возвращать список DEFAULT конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param schemaName наименование схемы в БД
     * @return список Constraint
     */
    List<Constraint> getDefaultByTable(String tableName, String schemaName);

    /**
     * Реализация должна возвращать список NOT NULL конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param schemaName наименование схемы в БД
     * @return список Constraint
     */
    List<Constraint> getNotNullByTable(String tableName, String schemaName);

    /**
     * Получить список с названиями таблиц, которые ссылаются на переданную
     *
     * @param tableName имя таблицы
     * @param schemaName имя схемы
     * @return список названий таблиц
     */
    List<String> getRelatedTables(String tableName, String schemaName);

    /**
     * Реализация должна возвращать ограничение PRIMARY KEY,
     * если оно присутствует в данной колонке
     * конкретнойтаблицы для конкретной БД
     *
     * @param tableName  наименование таблицы в БД
     * @param columnName наименование колонки в БД
     * @param schemaName  наименование схемы в БД
     * @return список Constraint
     */
    Constraint getPKConstraintForColumn(String tableName, String columnName, String schemaName);

    /**
     * Реализация должна возвращать UNIQUE,
     * если оно присуствует в данной колонке
     * конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param columnName наименование колонки в БД
     * @param schemaName наименование схемы в БД
     * @return ограничение
     */
    Constraint getUniqueConstraintForColumn(String tableName, String columnName, String schemaName);

    /**
     * Реализация должна возвращать ограничения CHECK,
     * если оно присуствует в данной колонке
     * конкретной таблицы для конкретной БД
     *
     * @param tableName  наименование таблицы в БД
     * @param columnName наименование колонки в БД
     * @param schemaName наименование схемы в БД
     * @return ограничение
     */
    Constraint getCheckConstraintForColumn(String tableName, String columnName, String schemaName);

    /**
     * Реализация должна  возвращать FOREIGN KEY,
     * если оно присутствует в данной колонке
     * конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param columnName наименование колонки в БД
     * @param schemaName наименование схемы в БД
     * @return ограничение
     */
    Constraint getFKConstraintForColumn(String tableName, String columnName, String schemaName);


    ForeignConstraintInfo getTargetForeignColumnNameAndTableName(String tableName, String columnName, String schemaName);

    /**
     * Реализация должна возвращать NOT NULL,
     * если оно присуствует в данной колонке
     * конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param columnName наименование колонки в БД
     * @param schemaName наименование схемы в БД
     * @return ограничение
     */
    Constraint getNotNullConstraintForColumn(String tableName, String columnName, String schemaName);

    /**
     * Реализация должна возвращать DEFAULT,
     * если оно присуствует в данной колонке
     * конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param columnName наименование колонки в БД
     * @param schemaName наименование схемы в БД
     * @return ограничение
     */
    Constraint getDefaultConstraintForColumn(String tableName, String columnName, String schemaName);

    /**
     * Реализация должна возвращать ограничение INDEX,
     * если оно присутствует в данной колонке
     * конкретной таблицы для конкретной БД
     *
     * @param tableName наименование таблицы в БД
     * @param columnName наименование колонки в БД
     * @param schemaName наименование схемы в БД
     * @return ограничение
     */
    Constraint getIndexConstraintForColumn(String tableName, String columnName, String schemaName);
}
