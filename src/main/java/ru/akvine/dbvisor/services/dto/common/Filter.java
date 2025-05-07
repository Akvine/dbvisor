package ru.akvine.dbvisor.services.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.akvine.compozit.commons.dto.NextPage;

@Data
@Accessors(chain = true)
public class Filter {
    @NotNull
    private NextPage nextPage;
}
