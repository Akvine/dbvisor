package ru.akvine.dbvisor.services;

import ru.akvine.dbvisor.services.dto.Table;

public interface ParseService {
    Table parse(byte[] content);
}
