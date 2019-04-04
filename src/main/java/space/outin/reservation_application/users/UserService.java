package space.outin.reservation_application.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import space.outin.reservation_application.reservations.Reservation;
import space.outin.reservation_application.reservations.ReservationsRepository;
import space.outin.reservation_application.restaurants.Restaurant;
import space.outin.reservation_application.restaurants.RestaurantsRepository;

@Service
public class UserService {
    private @Autowired RestaurantsRepository restaurantsRepository;
    private @Autowired UsersRepository usersRepository;
    private @Autowired ReservationsRepository reservationsRepository;

    public void deleteUser(User u) throws UserException {
        // Delete restaurant if has no reservations
        Restaurant ownedRestaurant = u.getRestaurant();
        if (ownedRestaurant != null) {
            List<Reservation> existingReservations = ownedRestaurant.getReservations();
            if (existingReservations.size() > 0) {
                throw new UserException(UserException.EXISTING_RESERVATIONS);
            }
            restaurantsRepository.delete(ownedRestaurant);
        }

        // Delete personal reservations
        List<Reservation> personalReservations = u.getReservations();
        reservationsRepository.deleteAll(personalReservations);

        // Delete user
        usersRepository.delete(u);
    }

    public static class UserException extends Exception {
        private static final long serialVersionUID = -4274438375126689145L;
        public static String EXISTING_RESERVATIONS = "Cannot delete associated restaurant because it has existing reservations. Delete reservations before proceeding.";

        public UserException(String e) {
            super(e);
        }
    }
}