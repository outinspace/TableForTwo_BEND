package space.outin.reservation_application.reservations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsRepository reservations;

    @PostMapping("/save")
    public Reservation save(@RequestBody Reservation r) {
        
        return reservations.save(r);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        reservations.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public Reservation getById(@PathVariable Integer id) {
        return reservations.getOne(id);
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
