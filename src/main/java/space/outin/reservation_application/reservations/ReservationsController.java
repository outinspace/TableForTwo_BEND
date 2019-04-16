package space.outin.reservation_application.reservations;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import space.outin.reservation_application.users.AuthSession;
import space.outin.reservation_application.users.User;
import space.outin.reservation_application.users.AuthSession.AuthenticationException;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private AuthSession authSession;

    @GetMapping("/my")
    public List<Reservation> getMyReservations() throws AuthenticationException {
        authSession.verifyAuthOrThrow();
        User current = authSession.fetchCurrentUser();
        return reservationsRepository.findAllByUser(current);
    }

    @PostMapping("/save")
    public Reservation save(@RequestBody @Valid Reservation r) {
        return reservationsRepository.save(r);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) throws ReservationException {
        Reservation reservation = reservationsRepository.getOne(id);
        if (reservation == null) {
            throw new ReservationException(ReservationException.RESERVATION_NONEXISTENT);
        }
        // Check if user owns reservation or if user owns restaurant who ownes reservation
        reservationsRepository.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public Reservation getById(@PathVariable Integer id) {
        return reservationsRepository.getOne(id);
    }

    public static class ReservationException extends Exception {
        public static String RESERVATION_NONEXISTENT = "Cannot perform action. Reservation does not exist.";
        public ReservationException(String s) {
            super(s);
        }
    }
}
