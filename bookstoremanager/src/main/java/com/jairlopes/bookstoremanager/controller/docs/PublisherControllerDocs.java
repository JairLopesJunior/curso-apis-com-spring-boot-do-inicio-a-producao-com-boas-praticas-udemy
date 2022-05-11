package com.jairlopes.bookstoremanager.controller.docs;

import com.jairlopes.bookstoremanager.dto.PublisherDTO;
import com.jairlopes.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.jairlopes.bookstoremanager.exception.PublisherNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

@Api("Publishers management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Publisher creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sucess publisher creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or user already registered on system")
    })
    PublisherDTO create(PublisherDTO publisherDTO) throws PublisherAlreadyExistsException;

    @ApiOperation(value = "Find publisher by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucess publisher found"),
            @ApiResponse(code = 400, message = "Publisher not found error")
    })
    PublisherDTO findById(Long id) throws PublisherNotFoundException;
}
