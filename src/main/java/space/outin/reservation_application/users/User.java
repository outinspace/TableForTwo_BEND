package space.outin.reservation_application.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import space.outin.reservation_application.reservations.Reservation;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.users.transfer.UserChanges;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = Access.READ_ONLY)
    private Integer id;

    @Email
    @NotBlank
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Length(min = 8)
    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @JsonProperty(access = Access.READ_ONLY)
    @OneToOne
    private Restaurant restaurant;

    @JsonProperty(access = Access.READ_ONLY)
    @CreationTimestamp
    Date created;

    @JsonProperty(access = Access.READ_ONLY)
    @UpdateTimestamp
    Date modified = new Date();

    public void applyChanges(UserChanges changes) {
        changes.getEmail().ifPresent(email -> this.email = email);
        changes.getPassword().ifPresent(password -> this.password = password);
        changes.getFirstName().ifPresent(firstName -> this.firstName = firstName);
        changes.getLastName().ifPresent(lastName -> this.lastName = lastName);
    }
}