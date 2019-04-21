package space.outin.reservation_application.restaurants;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import space.outin.reservation_application.restaurants.RestaurantsController.RestaurantException;
import space.outin.reservation_application.restaurants.transfer.RestaurantChanges;
import space.outin.reservation_application.server.ReservationApplication;
import space.outin.reservation_application.users.AuthSession;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.UsersRepository;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;
import space.outin.reservation_application.users.UsersController.UserAlreadyExistsException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationApplication.class)
public class RestaurantsControllerTest {
    private @Autowired RestaurantsController restController;
    private @Autowired RestaurantsRepository restRepository;
    private @Autowired UsersRepository usersRepository;
    private @Autowired AuthSession authSession;

    private User savedUser;
    private Restaurant savedRestaurant;

    @Before
    public void setup() throws UserAlreadyExistsException, AuthenticationException {
        savedUser = new User();
        savedUser.setEmail("test@email.com");
        savedUser.setPassword("password");
        savedUser.setFirstName("firstname");
        savedUser.setLastName("lastname");
        savedRestaurant = new Restaurant();
        savedRestaurant = restRepository.save(savedRestaurant);
        savedUser.setRestaurant(savedRestaurant);
        savedUser = usersRepository.save(savedUser);
        authSession.loginSession(savedUser.getEmail(), savedUser.getPassword());
    }

    @After
    public void breakdown() {
        authSession.logoutSession();
        usersRepository.delete(savedUser);
        restRepository.delete(savedRestaurant);
    }

    @Test
    @Transactional
    public void publishRestaurantWorks() throws RestaurantException, AuthenticationException {
        Restaurant restaurant = restController.publish();
        assertThat(restaurant.isPublished());
        List<Restaurant> restaurants = restRepository.findAllByPublished(true);
        assertThat(restaurants).contains(restaurant);
    }

    @Test
    @Transactional
    public void unpublishRestaurantWorks() throws RestaurantException, AuthenticationException {
        Restaurant restaurant = restController.unpublish();
        assertThat(!restaurant.isPublished());
        List<Restaurant> restaurantList = restRepository.findAllByPublished(true);
        assertThat(restaurantList).isEmpty();

    }

     @Test
     @Transactional
     public void updateRestaurantWorks() throws RestaurantException, AuthenticationException {
         RestaurantChanges changes = new RestaurantChanges();
         changes.setAddress(Optional.of("123 Main Street"));
         changes.setCapacity(Optional.of(50));
         changes.setDescription(Optional.of("Testing changes to restaurant"));
         changes.setName(Optional.of("Test Restaurant"));
         changes.setImageUrl(Optional.of("https://junit.org/junit4/images/junit5-banner.png"));
         Restaurant rest = restController.update(changes);
         assertThat(rest.getAddress()).isEqualTo("123 Main Street");
         assertThat(rest.getCapacity()).isEqualTo(50);
         assertThat(rest.getDescription()).contains("changes");
         assertThat(rest.getName()).contains("Test");
         assertThat(rest.getImageUrl()).contains("junit.org");
     }
}