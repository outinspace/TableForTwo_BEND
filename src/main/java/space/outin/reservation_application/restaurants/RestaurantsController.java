package space.outin.reservation_application.restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.restaurants.Restaurant;
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

    @PostMapping("/create")
    public Restaurant create(@RequestBody Restaurant r) throws RestaurantException {
        User owner = authSession.getCurrentUser();
        Restaurant ownedRestaurant = owner.getRestaurant();
        if (ownedRestaurant != null) {
            throw new RestaurantException(RestaurantException.MAX_ONE);
        }
        // TODO: validation
        Restaurant saved = restaurantsRepository.save(r);
        owner.setRestaurant(r);
        usersRepository.save(owner);
        return saved;
    }

    @PostMapping("/update/{id}")
    public Restaurant update(@PathVariable("id") Integer id, @RequestBody Restaurant r) {
        // TODO: validate restaurant object
        // TODO: Check credentials
        r.setId(id);
        return restaurantsRepository.save(r);
    }

    @PostMapping("/delete")
    public void delete() throws RestaurantException, AuthenticationException {
        authSession.verifyAuthOrThrow();
        User owner = authSession.getCurrentUser();
        Restaurant r = owner.getRestaurant();
        if (r == null) {
            throw new RestaurantException(RestaurantException.NONEXISTENT);
        }
        restaurantsRepository.deleteById(r.getId());
    }

    @GetMapping("/get/{id}")
    public Restaurant get(@PathVariable Integer id) {
        return restaurantsRepository.getOne(id);
    }

    public static class RestaurantException extends Exception {
        private static final long serialVersionUID = 6806844114816175549L;
        private static final String NONEXISTENT = "Restaurant does not exist.";
        private static final String MAX_ONE = "Only one restaurant can be owned per user.";
        public RestaurantException(String e) {
            super(e);
        }
    }
}