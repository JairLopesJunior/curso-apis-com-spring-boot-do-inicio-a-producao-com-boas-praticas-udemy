package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.builder.AuthorDTOBuilder;
import com.jairlopes.bookstoremanager.mapper.AuthorMapper;
import com.jairlopes.bookstoremanager.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setup() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
    }
}
