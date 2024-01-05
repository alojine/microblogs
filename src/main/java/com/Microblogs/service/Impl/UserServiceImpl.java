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
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new ResourceNotFoundException(String.format("User with username: %s already exists", user.getUsername()));
        }

        return userRepository.save(user);
    }

    @Override
    public User getById(UUID id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s does not exist.", id.toString())));
    }
}
