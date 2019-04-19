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
@SpringBootTest
public class ReservationApplicationTests {

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UsersController usersController;
	@Autowired
	private RestaurantsRepository restRepository;
	@Autowired
	private RestaurantsController restController;
	@Autowired
	private ReservationsRepository reseRepository;
	@Autowired
	private ReservationsController reseController;

	@Test
    public void userCreationTest() {

        long repoSize = usersRepository.count();
        User user = new User();
        user.setFirstName("testfirst"); user.setLastName("testlast"); user.setEmail("test@email.com"); user.setPassword("testpassword");
        try {
            usersController.create(user, false);
        } catch (Exception ex) {
            assertTrue(false);
        }
        if (repoSize+1 == usersRepository.count()) { // count would be what it started with +1 since added user
            assertTrue(true);
        } else { assertTrue(false); }

    }

    @Test
    public void findUserByEmailTest() {
        Optional<User> user = usersRepository.findOneByEmail("test@email.com");
        if (user.isPresent()) {
            User retUser = user.get();
            assertEquals(retUser.getFirstName(), "testfirst");
        } else { assertTrue(false); }
        
    }

	@Test
	public void userUpdateTest() {
		UserChanges changes = new UserChanges();
		String newEmail = "test2@email.com";
		Optional<String> opt = Optional.of(newEmail);
		changes.setEmail(opt);
		try {
			usersController.update(changes);
		} catch (Exception ex) {
			assertTrue(false);
		}
		//make sure it took by finding in repository by new email
		Optional<User> user = usersRepository.findOneByEmail(newEmail);
		if (user.isPresent()) {
			User retUser = user.get();
			assertEquals(retUser.getFirstName(), "testfirst");
		} else { assertTrue(false); }
	}

    @Test
    public void userDeletionTest() {

        long repoSize = usersRepository.count();
        DeleteConfirmation delCon = new DeleteConfirmation();
        delCon.setPassword("testpassword");
        try {
            usersController.delete(delCon);
        } catch (Exception ex) {
            assertTrue(false);
        }
        if (repoSize -1 == usersRepository.count()) { // desired count as we deleted an entry after we took reposize
            assertTrue(true);
        }

	}

	@Test
	public void contextLoads() {
	}

}

