package space.outin.reservation_application.restaurants;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.reservations.Reservation;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.transfer.RestaurantChanges;
import space.outin.reservation_application.users.AuthSession;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantsRepository restaurantsRepository;

    @Autowired
    private AuthSession authSession;

    @GetMapping("/all")
    public List<Restaurant> getAllRestaurants() {
        return restaurantsRepository.findAllByPublished(true);
    }

    @GetMapping("/favorites")
    public List<Restaurant> getMyFavoriteRestaurants() {
        User currentUser = authSession.fetchCurrentUser();
        List<Reservation> reservations = currentUser.getReservations();
        reservations.sort((a, b) -> a.getCreated().compareTo(b.getCreated()));
        return reservations.stream()
            .map(r -> r.getRestaurant())
            .distinct()
            .limit(10L)
            .collect(Collectors.toList());
    }

    @GetMapping("/reservations")
    public List<Reservation> getRestaurantsReservations() throws RestaurantException, AuthenticationException {
        Restaurant restaurant = fetchCurrentRestaurantOrThrow();
        return restaurant.getReservations();
    }

    @PostMapping("/update")
    public Restaurant update(@RequestBody @Valid RestaurantChanges changes)
            throws AuthenticationException, RestaurantException {
        Restaurant restaurant = fetchCurrentRestaurantOrThrow();
        restaurant.applyChanges(changes);
        restaurant.setRegistered(true);
        return restaurantsRepository.save(restaurant);
    }

    @PostMapping("/publish")
    public Restaurant publish() throws RestaurantException, AuthenticationException {
        Restaurant restaurant = fetchCurrentRestaurantOrThrow();
        if (!restaurant.isRegistered()) {
            throw new RestaurantException(RestaurantException.NOT_REGISTERED);
        }
        List<Restaurant> restaurantsWithSameName = restaurantsRepository.findAllByName(restaurant.getName())
            .stream().filter(r -> r.getId() != restaurant.getId())
            .collect(Collectors.toList());
        if (!restaurantsWithSameName.isEmpty()) {
            throw new RestaurantException(RestaurantException.NAME_IN_USE);
        }
        restaurant.setPublished(true);
        return restaurantsRepository.save(restaurant);
    }

    @PostMapping("/unpublish")
    public Restaurant unpublish() throws RestaurantException, AuthenticationException {
        Restaurant restaurant = fetchCurrentRestaurantOrThrow();
        restaurant.setPublished(false);
        return restaurantsRepository.save(restaurant);
    }

    @GetMapping("/get/{id}")
    public Restaurant get(@PathVariable Integer id) {
        return restaurantsRepository.findById(id).get();
    }

    private Restaurant fetchCurrentRestaurantOrThrow() throws RestaurantException, AuthenticationException {
        authSession.verifyAuthOrThrow();
        Restaurant restaurant = authSession.fetchCurrentUser().getRestaurant();
        if (restaurant == null) {
            throw new RestaurantException(RestaurantException.NONEXISTENT);
        }
        return restaurant;
    }

    public static class RestaurantException extends Exception {
        private static final long serialVersionUID = 6806844114816175549L;
        private static final String NONEXISTENT = "Restaurant does not exist.";
        private static final String NAME_IN_USE = "Name is already in use by another restaurant.";
        private static final String NOT_REGISTERED = "Finish restaurant registration before performing this action.";
        public RestaurantException(String e) {
            super(e);
        }
    }
}