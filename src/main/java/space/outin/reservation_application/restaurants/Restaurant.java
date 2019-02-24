package space.outin.reservation_application.restaurants;

import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import space.outin.reservation_application.reservations.AuthDetails;
import space.outin.reservation_application.reservations.Reservation;

@Data
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonProperty(access=Access.WRITE_ONLY)
    @Embedded
    private AuthDetails authDetails;

    private String name;
    private String imageUrl;
    private String description;
    private String address;
    private int capacity;

    @JsonIgnore
    @OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Reservation> reservations;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date modified;
}