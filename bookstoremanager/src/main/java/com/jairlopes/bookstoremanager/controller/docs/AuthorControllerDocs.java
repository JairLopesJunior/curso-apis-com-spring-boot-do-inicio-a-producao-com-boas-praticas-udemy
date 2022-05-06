package com.jairlopes.bookstoremanager.controller.docs;

import com.jairlopes.bookstoremanager.dto.AuthorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api("Authors management")
public interface AuthorControllerDocs {

    @ApiOperation(value = "Author creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sucess author creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field value or author already registered on system")
    })
    AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO);

    @ApiOperation(value = "Find author by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucess author found"),
            @ApiResponse(code = 404, message = "Author not found error code")
    })
    AuthorDTO findById(@PathVariable Long id);
}
