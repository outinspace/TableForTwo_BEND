package space.outin.reservation_application.reservations;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import space.outin.reservation_application.users.User;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, Integer> {

        List<Reservation> findAllByUser(User user);
}