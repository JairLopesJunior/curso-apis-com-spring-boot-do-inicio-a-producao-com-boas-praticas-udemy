package com.jairlopes.bookstoremanager.controller.docs;

import com.jairlopes.bookstoremanager.dto.MessageDTO;
import com.jairlopes.bookstoremanager.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("System users management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user creation"),
            @ApiResponse(code = 400, message = "Missing required field or an error on validadion field rules")
    })
    MessageDTO create(UserDTO userToCreateDTO);
}
