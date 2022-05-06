package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.dto.AuthorDTO;
import com.jairlopes.bookstoremanager.entity.Author;
import com.jairlopes.bookstoremanager.exception.AuthorAlreadyExistsException;
import com.jairlopes.bookstoremanager.exception.AuthorNotFoundException;
import com.jairlopes.bookstoremanager.mapper.AuthorMapper;
import com.jairlopes.bookstoremanager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO) {
        verifyIfExists(authorDTO.getName());
        Author authorToCreate = authorMapper.toModelo(authorDTO);
        Author createdAuthor = authorRepository.save(authorToCreate);
        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findById(Long id) {
        Author foundAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        return authorMapper.toDTO(foundAuthor);
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {
                    throw new AuthorAlreadyExistsException(authorName);
                });
    }
}
