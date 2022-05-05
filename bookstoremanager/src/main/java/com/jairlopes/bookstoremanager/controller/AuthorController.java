package com.jairlopes.bookstoremanager.controller;

import com.jairlopes.bookstoremanager.controller.docs.AuthorControllerDocs;
import com.jairlopes.bookstoremanager.dto.AuthorDTO;
import com.jairlopes.bookstoremanager.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController implements AuthorControllerDocs {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.create(authorDTO);
    }
}
