package ru.akvine.dbvisor.exceptions;

public class ConvertTypeException extends RuntimeException {
    public ConvertTypeException(String message, RuntimeException innerException) {
        super(message, innerException);
    }
}
