package com.example.service;

import com.example.dto.UserDto;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserLimits(){
        UserDto limits = new UserDto(10,10);

        when(userRepository.findUserLimits(4)).thenReturn(limits);

        UserDto result = userService.getUserLimits(4);

        assertEquals(10, result.getLimitNew());
        assertEquals(10, result.getLimitRepeat());
        verify(userRepository,times(1)).findUserLimits(4);
    }
}
