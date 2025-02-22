package ru.akvine.dbvisor.services.mappers;

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
}
