package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.services.dto.ConnectionInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetService {
    ResultSet get(Connection connection, ConnectionInfo info) throws SQLException;
}
