package space.outin.reservation_application.restaurants;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

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
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.UsersController;
import space.outin.reservation_application.users.UsersRepository;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;
import space.outin.reservation_application.users.UsersController.UserAlreadyExistsException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationApplication.class)
public class RestaurantsControllerTest {
    private @Autowired RestaurantsController restController;
    private @Autowired RestaurantsRepository restRepository;
    private @Autowired UsersRepository usersRepository;
    private @Autowired UsersController usersController;

    private User user;

    @Before
    public void setup() throws UserAlreadyExistsException, AuthenticationException {
        user = new User();
        user.setEmail("test@email.com"); user.setPassword("password");
        user = usersController.create(user, true);
    }
    @After
    public void breakdown() {
        usersRepository.delete(user);
    }

    @Test
    public void publishRestaurantWorks() throws RestaurantException, AuthenticationException { 
        Restaurant rest = restController.publish();
        assertThat(rest).isNotNull();
        List<Restaurant> restList = restRepository.findAllByPublished(true);
        assertThat(restList).isNotNull();
    }

    @Test
    public void unpublishRestaurantWorks() throws RestaurantException, AuthenticationException {
        Restaurant rest = restController.unpublish();
        assertThat(rest).isNotNull();
        List<Restaurant> restList = restRepository.findAllByPublished(false); //is this correct? will it find all unpublished entries
        assertThat(restList).isNotNull();

    }

    @Test
    public void updateRestaurantWorks() throws RestaurantException, AuthenticationException {
        RestaurantChanges changes = new RestaurantChanges();
        changes.setAddress(Optional.of("123 Main Street"));
        changes.setCapacity(Optional.of(50));
        changes.setDescription(Optional.of("Testing changes to restaurant"));
        changes.setName(Optional.of("Test Restaurant"));
        Restaurant rest = restController.update(changes);
        assertThat(rest.getAddress()).isEqualTo("123 Main Street");
        assertThat(rest.getCapacity()).isEqualTo(50);
        assertThat(rest.getDescription()).contains("changes");
        assertThat(rest.getName()).contains("Test");
    }
}