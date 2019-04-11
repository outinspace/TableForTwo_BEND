package space.outin.reservation_application.restaurants;

import java.util.List;

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
        return restaurantsRepository.save(restaurant);
    }

    @PostMapping("/publish")
    public Restaurant publish() throws RestaurantException, AuthenticationException {
        Restaurant restaurant = fetchCurrentRestaurantOrThrow();
        // TODO: Validate info
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
        return restaurantsRepository.getOne(id);
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
        public RestaurantException(String e) {
            super(e);
        }
    }
}