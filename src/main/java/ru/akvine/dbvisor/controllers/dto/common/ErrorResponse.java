package ru.akvine.dbvisor.controllers.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Error response status
 */
@Data
public class ErrorResponse implements Response {
    /**
     * Result response status
     */
    @NotNull
    private final ResponseStatus status = ResponseStatus.FAIL;

    /**
     * Result response code
     */
    private final String code;

    /**
     * Result response message
     */
    private final String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
