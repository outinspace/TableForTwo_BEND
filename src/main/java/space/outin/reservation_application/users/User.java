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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
import space.outin.reservation_application.reservations.Reservation;
import space.outin.reservation_application.restaurants.Restaurant;

@Data
@Entity
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
    @Min(8L)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Null
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Reservation> reservations;

    @Null
    @OneToOne(fetch=FetchType.LAZY)
    private Restaurant restaurant;

    @Null
    @JsonProperty(access = Access.READ_ONLY)
    @CreationTimestamp
    Date created;

    @Null
    @JsonProperty(access = Access.READ_ONLY)
    @UpdateTimestamp
    Date modified;
}