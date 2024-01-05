package com.Microblogs.service;

import com.Microblogs.model.User;

import java.util.UUID;

public interface UserService {
    User save(User user);

    User getById(UUID id);
}
