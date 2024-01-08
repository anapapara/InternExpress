package com.example.internexpress.validator;

public interface Validator<T> {
    /**
     * Validate an entity
     * @param entity - the entity to be validated
     * @throws ValidationException
     * if an enity is not valid
    */

    void validate(T entity) throws ValidationException;
}
