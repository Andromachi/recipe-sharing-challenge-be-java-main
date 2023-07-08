package net.azeti.challenge.recipe.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.azeti.challenge.recipe.authentication.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO: check anotation
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //TODO remove
    private Role role;
}
