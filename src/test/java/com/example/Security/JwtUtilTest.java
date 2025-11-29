package com.example.Security;

import com.example.Security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    JwtUtil jwt = new JwtUtil();

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
        JwtUtil shortJwt = new JwtUtil() {
            @Override
            protected long expirationMillis() {
                return 1000; // 1 секунда
            }
        };

        String token = shortJwt.generateToken("bob", 7);

        Thread.sleep(1500);

        assertTrue(shortJwt.isExpired(token));
    }
}
