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
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonProperty(access = Access.READ_ONLY)
    private Integer id;

    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Reservation> reservations;

    @OneToOne(fetch=FetchType.LAZY)
    private Restaurant restaurant;

    @JsonProperty(access = Access.READ_ONLY)
    @CreationTimestamp
    Date created;

    @JsonProperty(access = Access.READ_ONLY)
    @UpdateTimestamp
    Date modified;
}