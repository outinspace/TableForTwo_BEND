package space.outin.reservation_application.restaurants.transfer;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class RestaurantChanges {
    private Optional<@NotBlank String> name;
    private Optional<@URL String> imageUrl;
    private Optional<@NotBlank String> description;
    private Optional<@NotBlank String> address;
    private Optional<@NotNull Integer> capacity;
}