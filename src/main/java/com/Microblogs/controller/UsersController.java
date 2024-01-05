package com.Microblogs.controller;

import com.Microblogs.controller.User.UserDto;
import com.Microblogs.exception.RequestValidationException;
import com.Microblogs.mapper.UserMapper;
import com.Microblogs.model.User;
import com.Microblogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UsersController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UsersController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) {
        UUID id;
        try {
            id = UUID.fromString(userId);
        } catch (IllegalArgumentException ex) {
            throw new RequestValidationException("Invalid UUID format provided");
        }

        return new ResponseEntity<>(userMapper.userToUserDTO(userService.getById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.save(userMapper.userDTOtoUser(userDto)), HttpStatus.OK);
    }
}
