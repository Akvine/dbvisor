package ru.akvine.dbvisor.constants;

public class ErrorCodes {
    private ErrorCodes() throws IllegalAccessException {
        throw new IllegalAccessException("Calling " + ErrorCodes.class.getSimpleName() + " constructor is prohibited!");
    }

    public interface Validation {
        String DATABASE_TYPE_INVALID_ERROR = "databaseType.invalid.error";
    }
}
