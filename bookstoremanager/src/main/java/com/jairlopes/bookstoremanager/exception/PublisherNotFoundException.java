package com.jairlopes.bookstoremanager.exception;

public class PublisherNotFoundException extends Exception {

    public PublisherNotFoundException(Long id) {
        super(String.format("Publisher with id %s not exists!", id));
    }
}
