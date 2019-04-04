package space.outin.reservation_application.users;

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
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import space.outin.reservation_application.reservations.Reservation;
import space.outin.reservation_application.restaurants.Restaurant;

@Data
@Entity
@DynamicUpdate(true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = Access.READ_ONLY)
    private Integer id;

    @NotEmpty
    @Email
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotEmpty
    @Length(min = 8)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @JsonProperty(access = Access.READ_ONLY)
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Reservation> reservations;

    @JsonProperty(access = Access.READ_ONLY)
    @OneToOne
    private Restaurant restaurant;

    @JsonProperty(access = Access.READ_ONLY)
    @CreationTimestamp
    Date created;

    @JsonProperty(access = Access.READ_ONLY)
    @UpdateTimestamp
    Date modified;

    public void mergeChanges(User newUser) {
      if (newUser.email != null && !newUser.email.isEmpty()) {
        this.email = newUser.email;
      }
      if (newUser.password != null && !newUser.password.isEmpty()) {
        this.password = newUser.password;
      }
      if (newUser.firstName != null && !newUser.firstName.isEmpty()) {
        this.firstName = newUser.firstName;
      }
      if (newUser.lastName != null && !newUser.lastName.isEmpty()) {
        this.lastName = newUser.lastName;
      }
    }
}