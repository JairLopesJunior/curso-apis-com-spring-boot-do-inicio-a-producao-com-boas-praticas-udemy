package com.jairlopes.bookstoremanager.exception;

public class PublisherAlreadyExistsException extends Exception {

    public PublisherAlreadyExistsException(String name, String code) {
        super(String.format("Publisher with name %s or code %s already exists!", name, code));
    }
}
