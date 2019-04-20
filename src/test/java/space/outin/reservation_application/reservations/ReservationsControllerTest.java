package space.outin.reservation_application.reservations;

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
import org.springframework.test.context.junit4.SpringRunner;

import space.outin.reservation_application.restaurants.RestaurantsRepository;
import space.outin.reservation_application.server.ReservationApplication;
import space.outin.reservation_application.users.AuthSession;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;
import space.outin.reservation_application.users.UserService.UserException;
import space.outin.reservation_application.users.UsersController.UserAlreadyExistsException;
import space.outin.reservation_application.users.transfer.DeleteConfirmation;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationApplication.class)
public class ReservationsControllerTest {
    private @Autowired ReservationsController reseController;
    private @Autowired ReservationsRepository reseRepository;
    private @Autowired AuthSession authSession;
    private @Autowired RestaurantsRepository restRepo; 

    @Test
    public void createReservationWorks() {
        
    }

    @Test
    public void deleteReservationWorks() {

    }
    //not sure if want to test get one or get list of reservations
    @Test
    public void getOneWorksOrMyWorks() {

    }
}