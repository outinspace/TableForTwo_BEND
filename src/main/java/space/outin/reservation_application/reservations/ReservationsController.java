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
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.RestaurantsController;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private RestaurantsController restaurantsController;

    @Autowired
    private AuthSession authSession;

    @GetMapping("/my")
    public List<Reservation> getMyReservations() throws AuthenticationException {
        authSession.verifyAuthOrThrow();
        User current = authSession.fetchCurrentUser();
        return reservationsRepository.findAllByUser(current);
    }

    @PostMapping("/create")
    public Reservation create(@RequestBody @Valid Reservation r, @RequestParam(required = true) Integer restId) {
        r.setId(null);
        r.setUser(authSession.fetchCurrentUser());
        Restaurant restaurant = toList(restaurantsController.get(restId)).get(0);
        r.setRestaurant(restaurant);
        return reservationsRepository.save(r);
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
