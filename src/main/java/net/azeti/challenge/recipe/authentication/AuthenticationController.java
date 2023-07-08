package net.azeti.challenge.recipe.authentication;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.azeti.challenge.recipe.authentication.dto.AuthenticationRequest;
import net.azeti.challenge.recipe.authentication.dto.AuthenticationResponse;
import net.azeti.challenge.recipe.authentication.dto.RegisterRequest;
import net.azeti.challenge.recipe.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @Operation(summary = "Register a user. Role has to be USER or ADMIN but the distinction between the roles is not implemented yet")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    //TODO: what is the differnce with spring login
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(service.authenticate(request));
    }
}
