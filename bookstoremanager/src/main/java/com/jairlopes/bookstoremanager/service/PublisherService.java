package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.dto.PublisherDTO;
import com.jairlopes.bookstoremanager.entity.Publisher;
import com.jairlopes.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.jairlopes.bookstoremanager.exception.PublisherNotFoundException;
import com.jairlopes.bookstoremanager.mapper.PublisherMapper;
import com.jairlopes.bookstoremanager.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO) throws PublisherAlreadyExistsException {
        this.verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());

        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id) throws PublisherNotFoundException {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    private void verifyIfExists(String name, String code) throws PublisherAlreadyExistsException {
        Optional<Publisher> publicatedPublisher = publisherRepository
                .findByNameOrCode(name, code);
        if(publicatedPublisher.isPresent()){
            throw new PublisherAlreadyExistsException(name, code);
        }
    }
}
