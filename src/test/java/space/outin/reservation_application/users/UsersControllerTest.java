package space.outin.reservation_application.users;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import space.outin.reservation_application.restaurants.RestaurantsRepository;
import space.outin.reservation_application.server.ReservationApplication;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;
import space.outin.reservation_application.users.UserService.UserException;
import space.outin.reservation_application.users.UsersController.UserAlreadyExistsException;
import space.outin.reservation_application.users.transfer.DeleteConfirmation;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationApplication.class)
public class UsersControllerTest {

    private @Autowired UsersController usersController;
    private @Autowired UsersRepository usersRepository;
    private @Autowired RestaurantsRepository restaurantsRepository;
    private @Autowired AuthSession authSession;
    private @Autowired PasswordEncoder passwordEncoder;

    private User unsavedUser;

    @Before
    public void setup() {
        unsavedUser = new User();
        unsavedUser.setEmail("new@test.com");
        unsavedUser.setFirstName("firstname");
        unsavedUser.setLastName("lastname");
        unsavedUser.setPassword(passwordEncoder.encode("password"));
    }

    @Test
    public void createCustomerUserWorks() throws UserAlreadyExistsException, AuthenticationException {
        User savedUser = usersController.create(unsavedUser, false);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.created).isCloseTo(new Date(), TimeUnit.MINUTES.toMillis(1));
        assertThat(savedUser.getRestaurant()).isNull();
        usersRepository.delete(savedUser);
    }

    @Test
    public void createOwnerUserWorks() throws UserAlreadyExistsException, AuthenticationException {
        User savedUser = usersController.create(unsavedUser, true);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getRestaurant()).isNotNull();
        usersRepository.delete(savedUser);
    }

    @Test(expected = JpaSystemException.class)
    public void deleteCustomerUserWorks() throws AuthenticationException, UserException {
        User savedUser = usersRepository.save(unsavedUser);
        authSession.setUserId(Optional.of(savedUser.getId()));
        DeleteConfirmation confirmation = new DeleteConfirmation();
        confirmation.setPassword("password");
        usersController.delete(confirmation);
        authSession.setUserId(Optional.empty());
        usersRepository.getOne(savedUser.getId());
    }

    // @Test
    // @Transactional
    // public void deleteOwnerUserWorks() throws AuthenticationException, UserException {
    //   Restaurant r = new Restaurant();
    //   r = restaurantsRepository.save(r);
    //   User savedUser = usersRepository.save(unsavedUser);
    //   savedUser.setRestaurant(r);
    //   savedUser = usersRepository.save(savedUser);

    //   authSession.setUserId(Optional.of(savedUser.getId()));
    //   DeleteConfirmation confirmation = new DeleteConfirmation();
    //   confirmation.setPassword(savedUser.getPassword());
    //   usersController.delete(confirmation);
    //   authSession.setUserId(Optional.empty());
    //   usersRepository.getOne(savedUser.getId());
    // }
}