package space.outin.reservation_application.reservations;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import space.outin.reservation_application.reservations.ReservationsController.ReservationException;
import space.outin.reservation_application.reservations.transfer.ReservationChanges;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.RestaurantsRepository;
import space.outin.reservation_application.server.ReservationApplication;
import space.outin.reservation_application.users.AuthSession;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.UsersRepository;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;
import space.outin.reservation_application.users.UsersController.UserAlreadyExistsException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationApplication.class)
public class ReservationsControllerTest {
    private @Autowired ReservationsController reseController;
    private @Autowired ReservationsRepository reseRepository;
    private @Autowired AuthSession authSession;
    private @Autowired RestaurantsRepository restRepo; 
    private @Autowired UsersRepository usersRepository;
    private @Autowired PasswordEncoder passwordEncoder;

    private User savedUser;
    private Restaurant savedRestaurant;
    
    @Before
    public void setup() throws UserAlreadyExistsException, AuthenticationException {
        savedUser = new User();
        savedUser.setEmail("test@email.com");
        savedUser.setPassword(passwordEncoder.encode("password"));
        savedUser.setFirstName("firstname");
        savedUser.setLastName("lastname");
        savedRestaurant = new Restaurant();
        savedRestaurant.setRegistered(true);
        savedRestaurant.setCapacity(40);
        savedRestaurant.setId(1);
        savedRestaurant = restRepo.save(savedRestaurant);
        savedUser = usersRepository.save(savedUser);
        authSession.loginSession(savedUser.getEmail(), "password");
    }

    @After
    public void breakdown() {
        authSession.logoutSession();
        usersRepository.delete(savedUser);
        restRepo.delete(savedRestaurant);
    }

    @Test
    @Transactional
    public void createReservationWorks() throws ReservationException, AuthenticationException {
        Reservation savedReservation;
        Reservation unsavedReservation = new Reservation();
        unsavedReservation.setDate(new Date(new Date().getTime() + TimeUnit.MINUTES.toMillis(100)));
        unsavedReservation.setPeople(3);
        savedReservation = reseController.create(unsavedReservation, savedRestaurant.getId());
        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getUser()).isEqualTo(savedUser);
        assertThat(savedReservation.getPeople()).isEqualTo(3);
        reseRepository.delete(savedReservation);
    }

    @Test
    @Transactional
    public void updateReservationWorks() throws ReservationException, AuthenticationException {
        ReservationChanges changes = new ReservationChanges();
        Reservation changedReservation;
        Reservation reservation = new Reservation();
        reservation.setDate(new Date(new Date().getTime() + TimeUnit.MINUTES.toMillis(100)));
        reservation.setId(1);
        reservation.setNotes("Initial Notes");
        reservation.setPeople(2);
        reservation.setRestaurant(savedRestaurant);
        reservation.setUser(savedUser);
        changes.setDate(Optional.of(new Date(new Date().getTime() + TimeUnit.MINUTES.toMillis(200))));
        changes.setNotes(Optional.of("Test change"));
        changes.setPeople(Optional.of(3));
        reservation = reseRepository.save(reservation);
        changedReservation = reseController.update(changes, reservation.getId());
        assertThat(changedReservation.getUser()).isEqualTo(savedUser);
        assertThat(changedReservation.getNotes()).contains("change");
        assertThat(changedReservation.getPeople()).isEqualTo(3);
        reseRepository.delete(changedReservation);
    }
    @Test
    public void getMyWorks() throws ReservationException, AuthenticationException {
        List<Reservation> myReservations = null;
        Reservation savedReservation = new Reservation();
        savedReservation.setUser(savedUser);
        savedReservation.setRestaurant(savedRestaurant);
        savedReservation.setId(1);
        savedReservation.setPeople(3);
        savedReservation.setNotes("test reservation");
        savedReservation = reseRepository.save(savedReservation);
        assertThat(savedReservation).isNotNull();
        myReservations = reseController.getMyReservations();
        assertThat(myReservations).isNotNull();
        assertThat(myReservations.get(0).getId()).isEqualTo(savedReservation.getId());
        assertThat(myReservations.get(0).getPeople()).isEqualTo(3);
        assertThat(myReservations.get(0).getNotes()).contains("test");
        reseRepository.delete(savedReservation);
    }
}