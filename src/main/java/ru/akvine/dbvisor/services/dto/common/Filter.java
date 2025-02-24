package ru.akvine.dbvisor.services.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.controllers.dto.common.NextPage;

@Data
@Accessors(chain = true)
public class Filter {
    @NotNull
    private NextPage nextPage;
}
