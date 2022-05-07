package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.mapper.PublisherMapper;
import com.jairlopes.bookstoremanager.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }
}
