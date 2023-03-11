package com.example.socialnetworkgui.validation;

public interface Validator<E> {
    public void validate(E entity) throws ValidationException;
}
