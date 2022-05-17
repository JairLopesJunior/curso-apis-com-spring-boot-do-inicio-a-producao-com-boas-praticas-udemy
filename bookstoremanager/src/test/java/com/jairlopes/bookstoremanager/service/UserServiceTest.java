package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.builder.UserDTOBuilder;
import com.jairlopes.bookstoremanager.mapper.UserMapper;
import com.jairlopes.bookstoremanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
