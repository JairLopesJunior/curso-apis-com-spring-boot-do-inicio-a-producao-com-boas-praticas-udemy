package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.dto.MessageDTO;
import com.jairlopes.bookstoremanager.dto.UserDTO;
import com.jairlopes.bookstoremanager.entity.User;
import com.jairlopes.bookstoremanager.exception.UserAlreadyExistsException;
import com.jairlopes.bookstoremanager.exception.UserNotFoundException;
import com.jairlopes.bookstoremanager.mapper.UserMapper;
import com.jairlopes.bookstoremanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final static UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Long id) {
        userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        String email = userToCreateDTO.getEmail();
        String username = userToCreateDTO.getUsername();
        userRepository.findByEmailOrUsername(email, username)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(email, username);
                });

        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);

        String createdUsername = createdUser.getUsername();
        Long createdUserId = createdUser.getId();
        String createdUserMessage = String.format("User %s with ID %s successfully created", createdUsername, createdUserId);
        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }

    public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
        User foundUser = verifyAndGetIfExists(id);

        userToUpdateDTO.setId(foundUser.getId());
        User userToUpdate = userMapper.toModel(userToUpdateDTO);
        userToUpdate.setCreatedDate(foundUser.getCreatedDate());

        User updatedUser = userRepository.save(userToUpdate);
        return updatedMessage(updatedUser);
    }

    private User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private MessageDTO updatedMessage(User updatedUser) {
        String createdUsername = updatedUser.getUsername();
        Long createdId = updatedUser.getId();
        String createdUserMessage = String.format("User %s with ID %s successfully updated", createdUsername, createdId);
        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }

}
