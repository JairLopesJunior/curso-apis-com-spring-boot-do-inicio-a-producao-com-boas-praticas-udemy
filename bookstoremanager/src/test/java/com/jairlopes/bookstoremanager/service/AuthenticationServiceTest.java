package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.builder.UserDTOBuilder;
import com.jairlopes.bookstoremanager.dto.UserDTO;
import com.jairlopes.bookstoremanager.entity.User;
import com.jairlopes.bookstoremanager.mapper.UserMapper;
import com.jairlopes.bookstoremanager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenUsernameIsInformedThenUserShouldBeReturned() {
        UserDTO expectedFoundUserDTO = this.userDTOBuilder.buildUserDTO();
        User expectedFoundUser = this.userMapper.toModel(expectedFoundUserDTO);
        SimpleGrantedAuthority expectedUserRole = new SimpleGrantedAuthority("ROLE" + expectedFoundUserDTO.getRole().getDescription());
        String expectedUsername = expectedFoundUser.getUsername();

        when(this.userRepository.findByUsername(expectedUsername)).thenReturn(Optional.of(expectedFoundUser));

        UserDetails userDetails = this.authenticationService.loadUserByUsername(expectedUsername);

        assertThat(userDetails.getUsername(), is(equalTo(expectedFoundUser.getUsername())));
        assertThat(userDetails.getPassword(), is(equalTo(expectedFoundUser.getPassword())));
        assertTrue(userDetails.getAuthorities().contains(expectedUserRole));
    }

    @Test
    void whenInvalidUsernameIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedFoundUserDTO = this.userDTOBuilder.buildUserDTO();
        String expectedUsername = expectedFoundUserDTO.getUsername();

        when(this.userRepository.findByUsername(expectedUsername)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> this.authenticationService.loadUserByUsername(expectedUsername));
    }
}
