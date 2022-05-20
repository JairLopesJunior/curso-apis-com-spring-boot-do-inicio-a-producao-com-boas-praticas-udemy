package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.builder.UserDTOBuilder;
import com.jairlopes.bookstoremanager.dto.MessageDTO;
import com.jairlopes.bookstoremanager.dto.UserDTO;
import com.jairlopes.bookstoremanager.entity.User;
import com.jairlopes.bookstoremanager.exception.UserAlreadyExistsException;
import com.jairlopes.bookstoremanager.exception.UserNotFoundException;
import com.jairlopes.bookstoremanager.mapper.UserMapper;
import com.jairlopes.bookstoremanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setup() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenNewUserIsInformedThenItShouldBeCreated() {
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedCreationMessage = "User jairlopes with ID 1 successfully created";

        String email = expectedCreatedUserDTO.getEmail();
        String username = expectedCreatedUserDTO.getUsername();
        when(userRepository.findByEmailOrUsername(email, username))
                .thenReturn(Optional.empty());
        when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

        MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDuplicatedUser = userMapper.toModel(expectedDuplicatedUserDTO);

        String email = expectedDuplicatedUserDTO.getEmail();
        String username = expectedDuplicatedUserDTO.getUsername();
        when(userRepository.findByEmailOrUsername(email, username))
                .thenReturn(Optional.of(expectedDuplicatedUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedDuplicatedUserDTO));
    }

    @Test
    void whenValidUserIsInformedThenItShouldBeDeleted() {
        UserDTO expectedDeleteUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDeleteUser = userMapper.toModel(expectedDeleteUserDTO);
        var expectedDeletedUserId = expectedDeleteUserDTO.getId();

        when(userRepository.findById(expectedDeletedUserId)).thenReturn(Optional.of(expectedDeleteUser));
        doNothing().when(userRepository)
                .deleteById(expectedDeletedUserId);

        userService.delete(expectedDeletedUserId);
        verify(userRepository, times(1)).deleteById(expectedDeletedUserId);
    }

    @Test
    void whenInvalidUserIdIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        var expectedDeletedUserId = expectedDeletedUserDTO.getId();

        when(userRepository.findById(expectedDeletedUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(expectedDeletedUserId));
    }

    @Test
    void whenExistingUserIsInformedThenItShouldBeUpdated() {
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUpdatedUserDTO.setUsername("Jairupdate");
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);
        String expectedUpdatedMessage = "User Jairupdate with ID 1 successfully updated";

        when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.of(expectedUpdatedUser));
        when(userRepository.save(expectedUpdatedUser)).thenReturn(expectedUpdatedUser);

        MessageDTO successUpdatedMessage = userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO);

        assertThat(successUpdatedMessage.getMessage(), is(equalTo(expectedUpdatedMessage)));
    }

    @Test
    void whenNotExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUpdatedUserDTO.setUsername("Jairupdate");

        when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO));
    }
}
