package net.azeti.challenge.recipe.security.config;

import net.azeti.challenge.recipe.authentication.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    private JwtService jwtService;

    @MockBean
    private UserDetails mockUserDetails;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"); // set your test secret key
        mockUserDetails = mock(UserDetails.class);
    }

    @Test
    public void shouldGenerateToken() {
        when(mockUserDetails.getUsername()).thenReturn("testuser");
        String token = jwtService.generateToken(mockUserDetails);
        assertNotNull(token);
    }

    @Test
    public void shouldExtractUsername() {
        when(mockUserDetails.getUsername()).thenReturn("testuser");
        String token = jwtService.generateToken(mockUserDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void shouldExtractAllClaims() {
        when(mockUserDetails.getUsername()).thenReturn("testuser");
        String token = jwtService.generateToken(mockUserDetails);
        assertNotNull(jwtService.extractAllClaims(token));
    }

    @Test
    public void shouldCheckTokenIsValid() {
        when(mockUserDetails.getUsername()).thenReturn("testuser");
        String token = jwtService.generateToken(mockUserDetails);
        assertTrue(jwtService.isTokenValid(token, mockUserDetails));
    }


    @Test
    public void shouldGenerateTokenWithClaims() {
        when(mockUserDetails.getUsername()).thenReturn("testuser");
        Map<String, Object> claims = new HashMap<>();
        claims.put("claim1", "value1");
        String token = jwtService.generateToken(claims, mockUserDetails);
        assertNotNull(token);
        assertTrue(jwtService.extractAllClaims(token).containsKey("claim1"));
        assertEquals("value1", jwtService.extractAllClaims(token).get("claim1"));
    }
}
