package ru.akvine.dbvisor.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.exceptions.GetMetadataException;
import ru.akvine.dbvisor.services.MapperService;
import ru.akvine.dbvisor.services.MetadataService;
import ru.akvine.dbvisor.services.ResultSetService;
import ru.akvine.dbvisor.services.dto.ConnectionInfo;
import ru.akvine.dbvisor.services.dto.GetRelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.RelatedTables;
import ru.akvine.dbvisor.services.dto.metadata.TableMetadata;
import ru.akvine.dbvisor.utils.Asserts;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetadataServiceImpl implements MetadataService {
    private final ResultSetService resultSetService;
    private final MapperService mapperService;

    @Override
    public List<TableMetadata> getTables(DataSource source, ConnectionInfo info) {

        List<TableMetadata> tableMetadata = new ArrayList<>();
        try(Connection connection = source.getConnection();
            ResultSet resultSet = resultSetService.get(connection, info)) {

            while (resultSet.next()) {
                TableMetadata metadata = new TableMetadata();
                metadata.setDatabase(resultSet.getString("TABLE_CAT"));
                metadata.setSchema(resultSet.getString("TABLE_SCHEM"));
                metadata.setTableName(resultSet.getString("TABLE_NAME"));
                tableMetadata.add(metadata);
            }
        } catch (SQLException exception) {
            throw new GetMetadataException("Error while getting table metadata. Message = [%s]" + exception.getMessage());
        }


        return tableMetadata;
    }

    @Override
    public RelatedTables getRelatedTables(GetRelatedTables getRelatedTables) {
        Asserts.isNotNull(getRelatedTables);

        List<String> relatedTables = mapperService
                .getMapper(getRelatedTables.getDataSource(), getRelatedTables.getDatabaseType())
                .getRelatedTables(getRelatedTables.getTableName(), getRelatedTables.getSchema());
        return new RelatedTables()
                .setOwnerTableName(getRelatedTables.getTableName())
                .setRelatedTablesNames(relatedTables);
    }
}
