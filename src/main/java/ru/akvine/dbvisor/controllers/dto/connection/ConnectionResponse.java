package ru.akvine.dbvisor.controllers.dto.connection;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.controllers.dto.common.SuccessfulResponse;

import java.util.List;

@Data
@Accessors(chain = true)
public class ConnectionResponse extends SuccessfulResponse {
    private List<ConnectionDto> connections;
}
