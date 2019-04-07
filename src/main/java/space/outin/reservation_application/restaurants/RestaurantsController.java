package space.outin.reservation_application.restaurants;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.transfer.RestaurantChanges;
import space.outin.reservation_application.users.AuthSession;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.UsersRepository;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantsRepository restaurantsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthSession authSession;

    @PostMapping("/update")
    public Restaurant update(@RequestBody @Valid RestaurantChanges changes) 
            throws AuthenticationException, RestaurantException {
        authSession.verifyAuthOrThrow();
        Restaurant restaurant = authSession.fetchCurrentUser().getRestaurant();
        if (restaurant == null) {
            throw new RestaurantException(RestaurantException.NONEXISTENT);
        }
        restaurant.applyChanges(changes);
        return restaurantsRepository.save(restaurant);
    }

    @GetMapping("/get/{id}")
    public Restaurant get(@PathVariable Integer id) {
        return restaurantsRepository.getOne(id);
    }

    public static class RestaurantException extends Exception {
        private static final long serialVersionUID = 6806844114816175549L;
        private static final String NONEXISTENT = "Restaurant does not exist.";
        public RestaurantException(String e) {
            super(e);
        }
    }
}