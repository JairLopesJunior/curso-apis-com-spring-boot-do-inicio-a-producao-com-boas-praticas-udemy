package com.jairlopes.bookstoremanager.controller;

import com.jairlopes.bookstoremanager.controller.docs.PublisherControllerDocs;
import com.jairlopes.bookstoremanager.dto.PublisherDTO;
import com.jairlopes.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.jairlopes.bookstoremanager.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController implements PublisherControllerDocs {

    private PublisherService publisherService;

    @Autowired
    public PublisherController() {
        this.publisherService = publisherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherDTO create(@RequestBody @Valid PublisherDTO publisherDTO) throws PublisherAlreadyExistsException {
        return this.publisherService.create(publisherDTO);
    }
}
