package com.Microblogs.service;

import com.Microblogs.controller.User.UserDto;
import com.Microblogs.exception.ResourceNotFoundException;
import com.Microblogs.model.User;
import com.Microblogs.repository.UserRepository;
import com.Microblogs.service.Impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void whenSaveUser_withExistingEmail_thenThrowsResourceValidationException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(provideUser()));
        when(userRepository.save(any())).thenReturn(provideUser());

        assertThrows(ResourceNotFoundException.class, () -> userService.save(provideUser()));
    }

    @Test
    void whenSaveUser_thenReturnCorrectUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(provideUser());

        assertThat(userService.save(provideUser())).isEqualTo(provideUser());
    }

    @Test
    void whenGetById_withNotExistentId_thenThrowsResourceValidationException() {
        UUID notExistentUserId = UUID.fromString("0629a3ca-5452-4264-8dfb-67ead125d134");
        when(userRepository.getUserById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(notExistentUserId));
    }

    @Test
    void whenGetById_withExistingId_thenReturnCorrectUser() {
        when(userRepository.getUserById(any())).thenReturn(Optional.of(provideUser()));

        assertThat(userService.getById(provideUser().getId())).isEqualTo(provideUser());
    }

    @Test
    void whenGetByEmail_withNotExistentEmail_thenThrowsResourceValidationException() {
        String notExistentEmail = "Random@gmail.com";
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByEmail(notExistentEmail));
    }

    @Test
    void whenGetByEmail_withExistingEmail_thenReturnCorrectUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(provideUser()));

        assertThat(userService.getByEmail(provideUser().getEmail())).isEqualTo(provideUser());
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