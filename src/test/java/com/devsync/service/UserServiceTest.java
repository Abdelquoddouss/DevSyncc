package com.devsync.service;

import com.devsync.enums.UserType;
import com.devsync.model.User;
import com.devsync.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        EntityManagerFactory emf = mock(EntityManagerFactory.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(emf);
        userService = spy(new UserService(emf));
        userService.userRepository = userRepository;
    }


    @Test
    public void testAddUser_MissingEmail() {
        User user = new User(1L, "John", "Doe", null, "password", "user", UserType.USER, 1, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });

        assertEquals("User email is required", exception.getMessage());
        verify(userRepository, never()).addUser(user);
    }



    @Test
    public void testUpdateUser_MissingEmail() {
        User user = new User(1L, "John", "Doe", null, "password", "user", UserType.USER, 1, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(user);
        });

        assertEquals("User email is required", exception.getMessage());
        verify(userRepository, never()).updateUser(user);
    }



    @Test
    public void testFindUserById_Success() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "password", "user", UserType.USER, 1, 1);

        when(userRepository.findById(1L)).thenReturn(user);
        User result = userService.findUserById(1L);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(null);

        User result = userService.findUserById(1L);
        assertNull(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteUser_Success() {
        doNothing().when(userRepository).deleteUser(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteUser(1L);
    }

    @Test
    public void testFindAllUsers_Success() {
        List<User> users = Arrays.asList(
                new User(1L, "John", "Doe", "john.doe@example.com", "password", "user", UserType.USER, 1, 1),
                new User(2L, "Jane", "Doe", "jane.doe@example.com", "password", "user", UserType.USER, 1, 1)
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }
}
