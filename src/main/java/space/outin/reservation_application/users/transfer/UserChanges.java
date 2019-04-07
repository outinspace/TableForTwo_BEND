package space.outin.reservation_application.users.transfer;

import java.util.Optional;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserChanges {
    private Optional<@Email String> email = Optional.empty();
    private Optional<@Length(min = 8) String> password = Optional.empty();
    private Optional<String> firstName = Optional.empty();
    private Optional<String> lastName = Optional.empty();
}