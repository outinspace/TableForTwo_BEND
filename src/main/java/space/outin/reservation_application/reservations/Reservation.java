package space.outin.reservation_application.reservations;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Data;
import space.outin.reservation_application.reservations.transfer.ReservationChanges;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.users.User;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonProperty(access=Access.READ_ONLY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Restaurant restaurant;

    private Integer people;
    private Date date;
    @Column(columnDefinition = "VARCHAR(256)")
    private String notes;

    @JsonProperty(access=Access.READ_ONLY)
    @CreationTimestamp
    private Date created;

    @JsonProperty(access=Access.READ_ONLY)
    @UpdateTimestamp
    private Date modified = new Date();   

    public void applyChanges(ReservationChanges changes) {
        changes.getDate().ifPresent(date -> this.date = date);
        changes.getNotes().ifPresent(notes -> this.notes = notes);
        changes.getPeople().ifPresent(people -> this.people = people);
    }
}