package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.dto.PublisherDTO;
import com.jairlopes.bookstoremanager.entity.Publisher;
import com.jairlopes.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.jairlopes.bookstoremanager.exception.PublisherNotFoundException;
import com.jairlopes.bookstoremanager.mapper.PublisherMapper;
import com.jairlopes.bookstoremanager.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) throws PublisherNotFoundException {
        verifyIfExists(id);
        publisherRepository.deleteById(id);
    }

    private void verifyIfExists(String name, String code) throws PublisherAlreadyExistsException {
        Optional<Publisher> publicatedPublisher = publisherRepository
                .findByNameOrCode(name, code);
        if(publicatedPublisher.isPresent()){
            throw new PublisherAlreadyExistsException(name, code);
        }
    }

    private void verifyIfExists(Long id) throws PublisherNotFoundException {
        publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }
}
