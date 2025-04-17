package ru.akvine.dbvisor.services.database.url;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.services.dto.connection.ConnectionInfo;
import ru.akvine.dbvisor.utils.Asserts;

@Service
public class PostgreSQLUrlBuilder implements URLBuilder {
    @Override
    public String build(ConnectionInfo info) {
        Asserts.isNotNull(info);

        StringBuilder sb = new StringBuilder("jdbc:postgresql://")
                .append(info.getHost()).append(":")
                .append(info.getPort()).append("/")
                .append(info.getDatabaseName());
        if (StringUtils.isNotBlank(info.getSchemaName())) {
            sb.append("?currentSchema=").append(info.getSchemaName());
        }
        return sb.toString();
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.POSTGRESQL;
    }
}
