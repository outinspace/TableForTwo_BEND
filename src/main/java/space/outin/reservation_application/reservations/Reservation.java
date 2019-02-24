package space.outin.reservation_application.reservations;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.users.User;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Restaurant restaurant;

    private Integer people;
    private Date date;
    private String notes;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date modified;   
}