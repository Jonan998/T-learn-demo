package com.example.Security;

import com.example.config.AppProperties;
import com.example.config.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtUtilTest {

    private AppProperties props;
    private JwtUtil jwt;

    @BeforeEach
    void setUp() {
        props = mock(AppProperties.class);

        when(props.getSecret()).thenReturn("testsecretkeytestsecretkey123456");
        when(props.getExpirationMillis()).thenReturn(30_000L); // 30 секунд

        jwt = new JwtUtil(props);
    }

    @Test
    void testGenerateAndParseToken() {
        String token = jwt.generateToken("alice", 42);

        Claims claims = jwt.getClaims(token);

        assertEquals("alice", claims.getSubject());
        assertEquals(42, ((Number) claims.get("userId")).intValue());
        assertFalse(jwt.isExpired(token));
    }

    @Test
    void testExpiredToken() throws InterruptedException {
        AppProperties shortProps = mock(AppProperties.class);
        when(shortProps.getSecret()).thenReturn("shortsecretkeyshortsecretkey12345");
        when(shortProps.getExpirationMillis()).thenReturn(1000L);

        JwtUtil shortJwt = new JwtUtil(shortProps);

        String token = shortJwt.generateToken("bob", 7);

        Thread.sleep(1500);

        assertTrue(shortJwt.isExpired(token));
    }
}
