package net.azeti.challenge.recipe.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO: check anotation
public class AuthenticationRequest {
    private String email;
    private String password;
}
