package com.Microblogs.service.Impl;

import com.Microblogs.exception.ResourceNotFoundException;
import com.Microblogs.model.User;
import com.Microblogs.repository.UserRepository;
import com.Microblogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ResourceNotFoundException(String.format("User with email: %s already exists", user.getEmail()));
        }

        return userRepository.save(user);
    }

    @Override
    public User getById(UUID id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s does not exist.", id.toString())));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with email: %s does not exists", email)));
    }
}
