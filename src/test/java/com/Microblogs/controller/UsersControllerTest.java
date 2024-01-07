package com.Microblogs.controller;

import com.Microblogs.controller.User.UserDto;
import com.Microblogs.exception.ResourceNotFoundException;
import com.Microblogs.mapper.UserMapper;
import com.Microblogs.model.User;
import com.Microblogs.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    void whenGetUserById_withCorrectUserId_thenReturnCorrectUser() throws Exception {
        when(userService.getById(any())).thenReturn(provideUser());
        when(userMapper.userToUserDTO(any())).thenReturn(provideUserDto());
        UUID userId = UUID.fromString("21c22fa2-2050-4815-9dbb-3de0389a6bd7");

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(provideUser().getUsername()))
                .andExpect(jsonPath("$.email").value(provideUser().getEmail()))
                .andExpect(jsonPath("$.password").value(provideUser().getPassword()))
                .andReturn();
    }

    @Test
    void whenCreateUser_withCorrectUserDto_thenReturnIsOk() throws Exception {
        when(userService.save(any())).thenReturn(provideDomUser());
        when(userMapper.userDTOtoUser(any())).thenReturn(provideDomUser());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provideDomUser())))
                .andExpect(status().isCreated())
                .andReturn();
    }

    private static User provideDomUser() {
        User user = new User();
        user.setId(UUID.fromString("21c22fa2-2050-4815-9dbb-3de0389a6bd7"));
        user.setUsername("dom");
        user.setEmail("dom@gmail.com");
        user.setPassword("domdom");
        user.setCreatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));
        user.setUpdatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808") );

        return user;
    }

    private static User provideUser() {
        User user = new User();
        user.setId(UUID.fromString("21c22fa2-2050-4815-9dbb-3de0389a6bd7"));
        user.setUsername("tom");
        user.setEmail("tom@gmail.com");
        user.setPassword("tomtom");
        user.setCreatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));
        user.setUpdatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808") );

        return user;
    }

    private static UserDto provideUserDto() {
        return new UserDto(
                "tom",
                "tom@gmail.com",
                "tomtom"
        );
    }
}