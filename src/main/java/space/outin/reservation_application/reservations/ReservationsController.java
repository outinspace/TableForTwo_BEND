package space.outin.reservation_application.reservations;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.users.AuthSession;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.RestaurantsRepository;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    static final long ONE_MINUTE_IN_MILLIS=60000;

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private RestaurantsRepository restaurantsRepository;

    @Autowired
    private AuthSession authSession;

    @GetMapping("/my")
    public List<Reservation> getMyReservations() throws AuthenticationException {
        authSession.verifyAuthOrThrow();
        User current = authSession.fetchCurrentUser();
        return reservationsRepository.findAllByUser(current);
    }

    @PostMapping("/create/{restaurantId}")
    public Reservation create(
        @RequestBody @Valid Reservation reservation, 
        @PathVariable Integer restaurantId
    ) throws ReservationException {
        reservation.setId(null);
        reservation.setUser(authSession.fetchCurrentUser());
        Optional<Restaurant> restaurant = restaurantsRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            reservation.setRestaurant(restaurant.get());
        } else {
            throw new ReservationException(ReservationException.RESTAURANT_NONEXISTENT);
        } 
        checkDateAndCapacity(reservation);
        return reservationsRepository.save(reservation);
    }
    
    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) throws ReservationException {
        Reservation reservation = reservationsRepository.getOne(id);
        if (reservation == null) {
            throw new ReservationException(ReservationException.RESERVATION_NONEXISTENT);
        }
        // Check if user owns reservation or if user owns restaurant who ownes reservation
        reservationsRepository.deleteById(id);
    }
    
    @GetMapping("/get/{id}")
    public Reservation getById(@PathVariable Integer id) {
        return reservationsRepository.getOne(id);
    }
    
    public static class ReservationException extends Exception {
        public static String RESERVATION_NONEXISTENT = "Cannot perform action. Reservation does not exist.";
        public static String RESTAURANT_NONEXISTENT = "Cannot create reservation. Restaurant does not exist.";
        public static String RESTAURANT_AT_CAPACITY = "Cannot create reservation. Restaurant is at capacity";
        public static String TIME_IMPOSSIBLE = "Cannot create reservation. Date has already passed";
        public ReservationException(String s) {
            super(s);
        }
    }
    
    public static <T> List<T> toList(Optional<T> option) {
        return option.
        map(Collections::singletonList).
        orElse(Collections.emptyList());
    }
    
    private void checkDateAndCapacity (Reservation reservation) throws ReservationException {
        Restaurant restaurant = reservation.getRestaurant();
        Date date = reservation.getDate();
        Date time = new Date(); 
        if (date.compareTo(time) < 0) { //if before current time
            throw new ReservationException(ReservationException.TIME_IMPOSSIBLE);
        }
        List<Reservation> currentReservationsForRestaurant = restaurant.getReservations();
        int reseCount = 0;
        long t = date.getTime();
        Date afterAddingThirtyMins = new Date (t + (30 * ONE_MINUTE_IN_MILLIS));
        Date afterSubtractingThirtyMins = new Date (t - (30 * ONE_MINUTE_IN_MILLIS));
        for (Reservation rese : currentReservationsForRestaurant) {
            if (rese.getDate().compareTo(afterAddingThirtyMins) <= 0 && rese.getDate().compareTo(afterSubtractingThirtyMins) >= 0 ) {
                reseCount++;
            }
        }
        if (reseCount >= restaurant.getCapacity()) {
            throw new ReservationException(ReservationException.RESTAURANT_AT_CAPACITY);
        }
        
    }
}
