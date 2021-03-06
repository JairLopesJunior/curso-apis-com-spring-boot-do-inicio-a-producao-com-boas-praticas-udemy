package com.jairlopes.bookstoremanager.exception;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id %s not exists!", id));
    }
}
