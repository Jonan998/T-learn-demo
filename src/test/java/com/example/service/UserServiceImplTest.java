package com.example.service;

import com.example.dto.UserDto;
import com.example.dto.UserLimitsView; // <-- 1. Импортируем интерфейс-Проекцию
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

    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        userService = new UserServiceImpl(userRepository,null,null);
    }

    @Test
    void testGetUserLimits(){

        UserLimitsView limitsViewMock = mock(UserLimitsView.class);

        when(limitsViewMock.getLimitNew()).thenReturn(10);
        when(limitsViewMock.getLimitRepeat()).thenReturn(10);

        when(userRepository.findUserLimits(4)).thenReturn(limitsViewMock);

        UserDto result = userService.getUserLimits(4);

        assertEquals(10, result.getLimitNew());
        assertEquals(10, result.getLimitRepeat());

        verify(userRepository, times(1)).findUserLimits(4);
    }
}