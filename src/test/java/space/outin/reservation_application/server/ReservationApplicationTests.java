package space.outin.reservation_application.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import space.outin.reservation_application.reservations.ReservationsController;
import space.outin.reservation_application.reservations.ReservationsRepository;
import space.outin.reservation_application.restaurants.RestaurantsController;
import space.outin.reservation_application.restaurants.RestaurantsRepository;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.UsersController;
import space.outin.reservation_application.users.UsersRepository;
import space.outin.reservation_application.users.transfer.DeleteConfirmation;
import space.outin.reservation_application.users.transfer.UserChanges;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationApplication.class)
public class ReservationApplicationTests {

	@Test
	public void contextLoads() {
	}

}

