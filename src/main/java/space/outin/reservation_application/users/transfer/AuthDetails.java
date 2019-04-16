package space.outin.reservation_application.users.transfer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthDetails {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}