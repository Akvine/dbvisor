package ru.akvine.dbvisor.validators;

public interface Validator<T> {
    void validate(T object);
}
