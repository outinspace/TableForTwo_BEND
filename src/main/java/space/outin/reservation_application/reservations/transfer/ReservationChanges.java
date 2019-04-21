package space.outin.reservation_application.reservations.transfer;

import java.util.Date;
import java.util.Optional;

import lombok.Data;

@Data
public class ReservationChanges {
    private Optional<Date> date = Optional.empty();
    private Optional<Integer> people = Optional.empty();
    private Optional<String> notes = Optional.empty();
}