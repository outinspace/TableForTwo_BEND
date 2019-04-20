package space.outin.reservation_application.reservations;

import java.util.Collections;
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
import space.outin.reservation_application.users.UsersRepository;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.RestaurantsRepository;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private @Autowired ReservationsRepository reservationsRepository;
    private @Autowired RestaurantsRepository restaurantsRepository;
    private @Autowired UsersRepository usersRepository;

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
    ) throws ReservationException, AuthenticationException {
        authSession.verifyAuthOrThrow();
        reservation.setId(null);
        reservation.setUser(authSession.fetchCurrentUser());
        Optional<Restaurant> restaurant = restaurantsRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            reservation.setRestaurant(restaurant.get());
        } else {
            throw new ReservationException(ReservationException.RESTAURANT_NONEXISTENT);
        }
        // Capacity check
        // Date check
        return reservationsRepository.save(reservation);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) throws ReservationException, AuthenticationException {
        authSession.verifyAuthOrThrow();
        Reservation reservation = reservationsRepository.getOne(id);
        if (reservation == null) {
            throw new ReservationException(ReservationException.RESERVATION_NONEXISTENT);
        } else if (userOwnsReservationOrRestaurant(reservation, authSession.getUserId().get())) {
            throw new ReservationException(ReservationException.NOT_OWNER);
        }
        reservationsRepository.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public Reservation getById(@PathVariable Integer id) {
        return reservationsRepository.getOne(id);
    }

    public boolean userOwnsReservationOrRestaurant(Reservation reservation, Integer currentUserId) {
        if (reservation.getUser().getId() == currentUserId) {
            return true;
        }
        Optional<User> restaurantOwner = usersRepository.findOneByRestaurant(reservation.getRestaurant());
        if (restaurantOwner.isPresent() && restaurantOwner.get().getId() == currentUserId) {
            return true;
        }
        return false;
    }

    public static class ReservationException extends Exception {
        public static String RESERVATION_NONEXISTENT = "Reservation does not exist.";
        public static String RESTAURANT_NONEXISTENT = "Restaurant does not exist.";
        public static String NOT_OWNER = "You do not have permissions to perform this action.";
        public ReservationException(String s) {
            super(s);
        }
    }

    public static <T> List<T> toList(Optional<T> option) {
        return option.
                map(Collections::singletonList).
                orElse(Collections.emptyList());
    }
}
