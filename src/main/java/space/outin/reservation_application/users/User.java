package space.outin.reservation_application.users;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import space.outin.reservation_application.reservations.AuthDetails;
import space.outin.reservation_application.reservations.Reservation;
import space.outin.reservation_application.restaurants.Restaurant;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @JsonIgnore
    @Embedded
    private AuthDetails authDetails;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Reservation> reservations;

    @OneToOne(fetch=FetchType.LAZY)
    private Restaurant restaurant;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date modified;
}