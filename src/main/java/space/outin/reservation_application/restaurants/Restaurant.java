package space.outin.reservation_application.restaurants;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import space.outin.reservation_application.reservations.Reservation;
import space.outin.reservation_application.restaurants.transfer.RestaurantChanges;

@Data
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access=Access.READ_ONLY)
    private Integer id;

    private String name;
    private String imageUrl;
    private String description;
    private String address;
    private int capacity;

    @OneToMany(mappedBy="restaurant", fetch=FetchType.LAZY)
    private List<Reservation> reservations;

    @JsonProperty(access=Access.READ_ONLY)
    @CreationTimestamp
    private Date created;

    @JsonProperty(access=Access.READ_ONLY)
    @UpdateTimestamp
    private Date modified = new Date();

    public void applyChanges(RestaurantChanges changes) {
        changes.getName().ifPresent(name -> this.name = name);
        changes.getImageUrl().ifPresent(imageUrl -> this.imageUrl = imageUrl);
        changes.getDescription().ifPresent(description -> this.description = description);
        changes.getAddress().ifPresent(address -> this.address = address);
        changes.getCapacity().ifPresent(capacity -> this.capacity = capacity);
    }
}