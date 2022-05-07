package com.jairlopes.bookstoremanager.controller;

import com.jairlopes.bookstoremanager.controller.docs.PublisherControllerDocs;
import com.jairlopes.bookstoremanager.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController implements PublisherControllerDocs {

    private PublisherService publisherService;

    @Autowired
    public PublisherController() {
        this.publisherService = publisherService;
    }
}
