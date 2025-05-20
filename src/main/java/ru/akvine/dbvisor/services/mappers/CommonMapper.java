package ru.akvine.dbvisor.services.mappers;

import ru.akvine.dbvisor.services.dto.metadata.Constraint;

import java.util.List;

/**
 * Класс для получения метаинформации из таблицы (индексы, первичные ключи, ограничения и т.д.)
 * с помощью MyBatis
 */
public interface CommonMapper {

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
     * @param schemName  наименование схемы в БД
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
