package space.outin.reservation_application.reservations;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.users.AuthSession;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.UsersRepository;
import space.outin.reservation_application.reservations.transfer.ReservationChanges;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.RestaurantsRepository;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    static final long ONE_MINUTE_IN_MILLIS=60000;

    private @Autowired ReservationsRepository reservationsRepository;
    private @Autowired RestaurantsRepository restaurantsRepository;
    private @Autowired UsersRepository usersRepository;

    private @Autowired AuthSession authSession;

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
        checkDateAndCapacity(reservation);
        return reservationsRepository.save(reservation);
    }

    @PostMapping("/update/{id}")
    public Reservation update(@RequestBody @Valid ReservationChanges changes, @PathVariable Integer id)
            throws ReservationException, AuthenticationException {
        authSession.verifyAuthOrThrow();
        Optional<Reservation> reservation = reservationsRepository.findById(id);
        if (!reservation.isPresent()) {
            throw new ReservationException(ReservationException.RESERVATION_NONEXISTENT);
        }
        Reservation existingReservation = reservation.get();
        int reservationUserId = existingReservation.getUser().getId();
        int currentUserId = authSession.getUserId().get();
        if (reservationUserId != currentUserId) {
            throw new ReservationException(ReservationException.INVALID_PERMISSIONS);
        }
        existingReservation.applyChanges(changes);
        checkDateAndCapacity(existingReservation);
        return reservationsRepository.save(existingReservation);
    }
    
    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) throws ReservationException, AuthenticationException {
        authSession.verifyAuthOrThrow();
        Reservation reservation = reservationsRepository.getOne(id);
        if (reservation == null) {
            throw new ReservationException(ReservationException.RESERVATION_NONEXISTENT);
        } else if (!userOwnsReservationOrRestaurant(reservation, authSession.getUserId().get())) {
            throw new ReservationException(ReservationException.INVALID_PERMISSIONS);
        }
        reservationsRepository.deleteById(id);
    }
    
    @GetMapping("/get/{id}")
    public Reservation getById(@PathVariable Integer id) {
        return reservationsRepository.getOne(id);
    }

    public boolean userOwnsReservationOrRestaurant(Reservation reservation, Integer currentUserId) {
        if (reservation.getUser().getId().equals(currentUserId)) {
            return true;
        }
        Optional<User> restaurantOwner = usersRepository.findOneByRestaurant(reservation.getRestaurant());
        if (restaurantOwner.isPresent() && restaurantOwner.get().getId().equals(currentUserId)) {
            return true;
        }
        return false;
    }

    @PostMapping("/completed/{completed}/{id}")
    public Reservation markReservationCompleted(@PathVariable boolean completed, @PathVariable Integer id) throws ReservationException, AuthenticationException {
        authSession.verifyAuthOrThrow();
        Optional<Reservation> r = reservationsRepository.findById(id);
        if (!r.isPresent()) {
            throw new ReservationException(ReservationException.RESERVATION_NONEXISTENT);
        }
        Reservation reservation = r.get();
        if (!userOwnsReservationOrRestaurant(reservation, authSession.getUserId().get())) {
            throw new ReservationException(ReservationException.INVALID_PERMISSIONS);
        }
        reservation.setCompleted(completed);
        return reservationsRepository.save(reservation);
    }
    
    private void checkDateAndCapacity (Reservation reservation) throws ReservationException {
        Restaurant restaurant = reservation.getRestaurant();
        Date date = reservation.getDate();
        Date timeLimit = new Date(new Date().getTime() + 3600*1000);
        if (date.before(timeLimit)) {
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

    public static class ReservationException extends Exception {
        public static String RESERVATION_NONEXISTENT = "Reservation does not exist.";
        public static String RESTAURANT_NONEXISTENT = "Restaurant does not exist.";
        public static String RESTAURANT_AT_CAPACITY = "Restaurant is at capacity.";
        public static String TIME_IMPOSSIBLE = "Reservation must be placed at least an hour in advance.";
        public static String INVALID_PERMISSIONS = "You do not have permissions to perform this action.";
        public ReservationException(String s) {
            super(s);
        }
    }
}
