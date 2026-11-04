package solutions.simurgh.core.identity.api.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank
        @Size(max = 100)
        String username,
        @Email
        @Size(max = 254)
        String email,
        @Size(max = 100)
        String firstname,
        @Size(max = 100)
        String lastname
) {}
