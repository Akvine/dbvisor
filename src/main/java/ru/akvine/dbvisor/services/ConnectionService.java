package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.services.domain.Connection;
import ru.akvine.dbvisor.services.dto.connection.CreateConnection;

import java.util.List;

public interface ConnectionService {
    List<Connection> list();

    Connection create(CreateConnection connection);
}
