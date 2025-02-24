package ru.akvine.dbvisor.exceptions;

public class CheckConnectionException extends RuntimeException {
    public CheckConnectionException(String message) {
        super(message);
    }
}
